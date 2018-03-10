package com.itlibrium.cooldomain.model;

public class BillingPoliciesFactoryImpl implements BillingPoliciesFactory {

    private final ClientRepository clientRepository;
    private final SingleServicePolicy singleServicePolicy;
    private final ServicePerActionPolicy servicePerActionPolicy;

    public BillingPoliciesFactoryImpl(ClientRepository clientRepository, PricingPolicy pricingPolicy) {
        this.clientRepository = clientRepository;
        this.singleServicePolicy = new SingleServicePolicy(pricingPolicy);
        this.servicePerActionPolicy = new ServicePerActionPolicy(pricingPolicy);
    }

    @Override
    public BillingPolicy getPolicyFor(ServiceOrder serviceOrder) {
        Guid clientId = serviceOrder.getClientId();
        BillingPreferences billingPreferences = clientRepository.getBillingPreferences(clientId);
        switch(billingPreferences) {
            case SINGLE_SERVICE:
                return singleServicePolicy;
            case SERVICE_PER_SERVICE_ACTION:
                return servicePerActionPolicy;
            default:
                throw new IllegalStateException("Unkonwn billing prefernces!");
        }


    }
}
