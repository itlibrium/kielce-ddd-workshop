package com.itlibrium.cooldomain.model;

import java.util.Collection;

public class CloseServiceOrderHandler {

    private final ServiceOrderRepository _serviceOrderRepository;

    private final ClientRepository _clientRepository;
    private final ServiceRepository _serviceRepository;
    private final BillingPolicy _billingPolicy;

    public CloseServiceOrderHandler(ServiceOrderRepository _serviceOrderRepository, ClientRepository _clientRepository,
                                    ServiceRepository _serviceRepository, BillingPolicy _billingPolicy) {
        this._serviceOrderRepository = _serviceOrderRepository;
        this._clientRepository = _clientRepository;
        this._serviceRepository = _serviceRepository;
        this._billingPolicy = _billingPolicy;
    }

    public void handle(CloseServiceOrderCommand command) {
        ServiceOrder serviceOrder = _serviceOrderRepository.GetById(command.getServiceOrderId());
        serviceOrder.close();

        Collection<Service> services = _billingPolicy.createServicesFor(serviceOrder);
        Money totalPrice = services.stream().map(Service::getPrice).reduce(Money.ZERO, Money::sum);

        PaymentType paymentType = PaymentType.values()[command.getPaymentType()];
        Client client = _clientRepository.getById(serviceOrder.getClientId());
        if (!client.canPay(totalPrice, paymentType))
            throw new BusinessException();

        _serviceOrderRepository.Save(serviceOrder);
        for (Service service : services) {
            _serviceRepository.save(service);
        }
    }
}
