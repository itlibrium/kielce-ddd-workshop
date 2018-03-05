package com.itlibrium.cooldomain.model

import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

class AppTests extends Specification {


    private final _client = Client.create(Money.ZERO, false);

    private final ServiceOrderRepository _serviceOrderRepository = Mock()
    private final ServiceRepository _serviceRepository = Mock()
    //TODO - stub with service action with a given price
    private final ServiceRepository _serviceActionRepository = Mock()
    //TODO - stub for client
    private final ClientRepository _clientRepository = Mock()

    private final OpenServiceOrderHandler _openServiceOrderHandler = new OpenServiceOrderHandler()
    private final AddServiceActionHandler _addServiceActionHandler = new AddServiceActionHandler()
    private final CloseServiceOrderHandler _closeServiceOrderHandler = new CloseServiceOrderHandler()

    private final LocalDateTime _now = LocalDateTime.of(2017, 1, 30, 15, 0, 0)

    private static final int PRICE_PER_HOUR = 100;
    private static final int SERVICE_PRICE = 150;

    private final Guid _serviceActionTypeId = new Guid("B90A7F71-AE0A-425D-9A7D-D1D2F47D1BF5");
    private final Guid _clientId;


    private Guid _serviceOrderId;

    private PaymentType _paymentType = PaymentType.IMMEDIATE;


    def "New open service order available"()
    {
//        BddScenario
//                .Given<Fixture>()
//            .When(f => f.OpenServiceOrder())
//            .Then(f => f.NewOpenServiceOrderCanByFoundById())
//            .Test();
    }

    def "Can not close service order when client can not pay"()
    {
//        BddScenario
//                .Given<Fixture>()
//            .And(f => f.OpenServiceOrder())
//            .And(f => f.OneServiceActionsForWhichClientCanNotPay())
//            .When(f => f.CloseServiceOrder())
//            .Then((f, e) => f.BusinessErrorIsRaised(e))
//            .Test();
    }

    def "Services are created on closing service order"()
    {
//        BddScenario
//                .Given<Fixture>()
//            .And(f => f.OpenServiceOrder())
//            .And(f => f.TwoServiceActionsForWhichClientCanPay())
//            .When(f => f.CloseServiceOrder())
//            .Then(f => f.ServiceOrderIsClosed())
//            .And(f => f.OneServiceIsPresentInSystem())
//            .Test();
    }



        private void OpenServiceOrder()
        {
            _serviceOrderId = _openServiceOrderHandler.Handle(new OpenServiceOrderCommand(_clientId));
        }

        private void TwoServiceActionsForWhichClientCanPay()
        {
            CreateServiceAction(LocalDateTime.of(2017, 1, 30, 12, 0, 0), 1);
            CreateServiceAction(LocalDateTime.of(2017, 1, 30, 13, 0, 0), 0.5);
            _paymentType = PaymentType.IMMEDIATE;
        }

        private void OneServiceActionsForWhichClientCanNotPay()
        {
            CreateServiceAction(LocalDateTime.of(2017, 1, 30, 12, 0, 0), 1);
            _paymentType = PaymentType.DEFERRED;
        }

        private void CreateServiceAction(LocalDateTime start, double durationInHours)
        {
            _addServiceActionHandler
                    .handle(new AddServiceActionCommand(
                    _serviceOrderId,
                    _serviceActionTypeId,
                    start,
                    start.plusHours((int)(60 * durationInHours))));
        }

        private void CloseServiceOrder()
        {
            _closeServiceOrderHandler.handle(new CloseServiceOrderCommand(_serviceOrderId, _paymentType.ordinal()))
        }

        private void NewOpenServiceOrderCanByFoundById()
        {
            ServiceOrder serviceOrder = _serviceOrderRepository.GetById(_serviceOrderId);
            serviceOrder.addServiceAction(new ServiceAction(Guid.newGuid(), Duration.ZERO));
        }

        private void ServiceOrderIsClosed()
        {
            ServiceOrder serviceOrder = _serviceOrderRepository.GetById(_serviceOrderId);
            serviceOrder.addServiceAction(new ServiceAction(Guid.newGuid(), Duration.ZERO));
            //thrown BusinessException
        }

        private void OneServiceIsPresentInSystem()
        {
            Collection<Service> services = _serviceRepository.getAll()
            services.size() == 1
            services[0].getPrice() == Money.fromDouble(SERVICE_PRICE)
        }

}
