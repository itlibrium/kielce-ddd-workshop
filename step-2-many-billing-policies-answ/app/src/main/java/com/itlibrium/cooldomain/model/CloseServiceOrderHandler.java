package com.itlibrium.cooldomain.model;

import java.util.Collection;

public class CloseServiceOrderHandler {

    private final ServiceOrderRepository _serviceOrderRepository;

    private final ClientRepository _clientRepository;
    private final ServiceRepository _serviceRepository;
    private final BillingPoliciesFactory billingPoliciesFactory;

    public CloseServiceOrderHandler(ServiceOrderRepository _serviceOrderRepository, ClientRepository _clientRepository,
                                    ServiceRepository _serviceRepository, BillingPoliciesFactory billingPoliciesFactory) {
        this._serviceOrderRepository = _serviceOrderRepository;
        this._clientRepository = _clientRepository;
        this._serviceRepository = _serviceRepository;
        this.billingPoliciesFactory = billingPoliciesFactory;
    }

    public void handle(CloseServiceOrderCommand command) {
        ServiceOrder serviceOrder = _serviceOrderRepository.GetById(command.getServiceOrderId());
        serviceOrder.close();

        BillingPolicy billingPolicy = billingPoliciesFactory.getPolicyFor(serviceOrder);

        Collection<Service> services = billingPolicy.createServicesFor(serviceOrder);
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
