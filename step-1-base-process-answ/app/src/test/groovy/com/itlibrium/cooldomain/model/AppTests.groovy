package com.itlibrium.cooldomain.model

import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

class AppTests extends Specification {

    private final ServiceOrderRepository _serviceOrderRepository = new ServiceOrderTestRepo()
    private final ServiceRepository _serviceRepository = Mock()
    private final ServiceActionRepository _serviceActionRepository = Mock()
    private final ClientRepository _clientRepository = Mock()

    private final OpenServiceOrderHandler _openServiceOrderHandler = new OpenServiceOrderHandler(_serviceOrderRepository)
    private final AddServiceActionHandler _addServiceActionHandler = new AddServiceActionHandler(_serviceOrderRepository)
    private final CloseServiceOrderHandler _closeServiceOrderHandler = new CloseServiceOrderHandler()

    private final LocalDateTime _now = LocalDateTime.of(2017, 1, 30, 15, 0, 0)

    private static final int PRICE_PER_HOUR = 100
    private static final int SERVICE_PRICE = 150
    private static final CLIENT = Client.create(Money.ZERO, false)

    private final Guid _serviceActionTypeId = new Guid("B90A7F71-AE0A-425D-9A7D-D1D2F47D1BF5")


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
        CreateServiceAction(LocalDateTime.of(2017, 1, 30, 12, 0, 0), 1)
        CreateServiceAction(LocalDateTime.of(2017, 1, 30, 13, 0, 0), 0.5)
        _paymentType = PaymentType.IMMEDIATE
    }

    private void OneServiceActionsForWhichClientCanNotPay()
    {
        CreateServiceAction(LocalDateTime.of(2017, 1, 30, 12, 0, 0), 1)
        _paymentType = PaymentType.DEFERRED
    }

    private void CreateServiceAction(LocalDateTime start, double durationInHours)
    {
        _addServiceActionHandler
                .handle(new AddServiceActionCommand(
                _serviceOrderId,
                _serviceActionTypeId,
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

    private void ServiceOrderIsClosed()
    {
        ServiceOrder serviceOrder = _serviceOrderRepository.GetById(_serviceOrderId)
        serviceOrder.addServiceAction(new ServiceAction(Guid.newGuid(), Duration.ZERO))
        //thrown BusinessException
    }

    private void OneServiceIsPresentInSystem()
    {
        Collection<Service> services = _serviceRepository.getAll()
        services.size() == 1
        services[0].getPrice() == Money.fromDouble(SERVICE_PRICE)
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

}
