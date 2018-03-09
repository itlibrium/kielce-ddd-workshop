package com.itlibrium.cooldomain.model

import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

class AppTests extends Specification {

    private final ServiceOrderRepository _serviceOrderRepository = new ServiceOrderTestRepo()
    private final ServiceRepository _serviceRepository =  new ServiceTestRepo()
    private final ServiceActionRepository _serviceActionRepository = Mock()
    private final ClientRepository _clientRepository = Mock()

    private final LabourCostPolicy labourCostPolicy = new LabourCostPolicy(_serviceActionRepository)
    private final BillingPolicy singleServicePolicy = new SingleServicePolicy(labourCostPolicy)
    private final OpenServiceOrderHandler _openServiceOrderHandler = new OpenServiceOrderHandler(_serviceOrderRepository)
    private final AddServiceActionHandler _addServiceActionHandler = new AddServiceActionHandler(_serviceOrderRepository)
    private final CloseServiceOrderHandler _closeServiceOrderHandler =
            new CloseServiceOrderHandler(_serviceOrderRepository, _clientRepository, _serviceRepository, singleServicePolicy)

    private final LocalDateTime _now = LocalDateTime.of(2017, 1, 30, 15, 0, 0)

    private static final Money PRICE_PER_HOUR = Money.fromDouble(100)
    private static final Money SERVICE_PRICE = Money.fromDouble(150)
    private static final CLIENT = Client.create(Money.ZERO, false)

    def NOON = LocalDateTime.of(2017, 1, 30, 12, 0, 0)
    def ONE_PM = LocalDateTime.of(2017, 1, 30, 13, 0, 0)

    private Guid _serviceOrderId
    private PaymentType _paymentType = PaymentType.IMMEDIATE


    def setup() {
        _clientRepository.getById(_) >> CLIENT
        _serviceActionRepository.getPricePerHourFor(_) >> PRICE_PER_HOUR

    }

    def "New open service order available"() {
        when:
        OpenServiceOrder()
        then:
        NewOpenServiceOrderCanByFoundById()
    }

    def "Can not close service order when client can not pay"()
    {
        given:
        OpenServiceOrder()
        OneServiceActionsForWhichClientCanNotPay()
        when:
        CloseServiceOrder()
        then:
        thrown BusinessException
    }

    def "Services are created on closing service order"()
    {
        given:
        OpenServiceOrder()
        TwoServiceActionsForWhichClientCanPay()
        when:
        CloseServiceOrder()
        then:
        ServiceOrderIsClosed()
        OneServiceIsPresentInSystem()
    }



    private void OpenServiceOrder()
    {
        _serviceOrderId = _openServiceOrderHandler.Handle(new OpenServiceOrderCommand(CLIENT.getId()))
    }

    private void TwoServiceActionsForWhichClientCanPay()
    {
        CreateServiceAction(NOON, 1, new Guid("A") )
        CreateServiceAction(ONE_PM, 0.5, new Guid("B"))
        _paymentType = PaymentType.IMMEDIATE
    }

    private void OneServiceActionsForWhichClientCanNotPay()
    {
        CreateServiceAction(NOON, 1, new Guid("A"))
        _paymentType = PaymentType.DEFERRED
    }

    private void CreateServiceAction(LocalDateTime start, double durationInHours, Guid typeId)
    {
        _addServiceActionHandler
                .handle(new AddServiceActionCommand(
                _serviceOrderId,
                typeId,
                start,
                start.plusHours((int)(60 * durationInHours))))
    }

    private void CloseServiceOrder()
    {
        _closeServiceOrderHandler.handle(new CloseServiceOrderCommand(_serviceOrderId, _paymentType.ordinal()))
    }

    private void NewOpenServiceOrderCanByFoundById()
    {
        ServiceOrder serviceOrder = _serviceOrderRepository.GetById(_serviceOrderId)
        serviceOrder.addServiceAction(new ServiceAction(Guid.newGuid(), Duration.ZERO))
    }

    private boolean ServiceOrderIsClosed() {
        try {
            ServiceOrder serviceOrder = _serviceOrderRepository.GetById(_serviceOrderId)
            serviceOrder.addServiceAction(new ServiceAction(Guid.newGuid(), Duration.ZERO))
        } catch (BusinessException e) {
            return true
        }
        return false
    }

    private void OneServiceIsPresentInSystem()
    {
        Collection<Service> services = _serviceRepository.getAll()
        services.size() == 1
        services[0].getPrice() == SERVICE_PRICE
    }


    private class ServiceOrderTestRepo implements ServiceOrderRepository {

        Map<Guid, ServiceOrder> store = new HashMap<>()

        @Override
        ServiceOrder GetById(Guid serviceOrderId) {
            return store.get(serviceOrderId);
        }

        @Override
        void Save(ServiceOrder serviceOrder) {
            store.put(serviceOrder.getId(), serviceOrder);
        }
    }

    private class ServiceTestRepo implements ServiceRepository {

        Map<Guid, Service> store = new HashMap<>()

        @Override
        Collection<Service> getAll() {
            return store.values()
        }

        @Override
        void save(Service service) {
            store.put(service.getId(), service)
        }
    }

}
