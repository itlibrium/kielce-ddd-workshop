package com.itlibrium.cooldomain.model;

public class BillingPoliciesFactoryImpl implements BillingPoliciesFactory {

    private final ClientRepository clientRepository;
    private final PricingPolicy pricingPolicy;

    public BillingPoliciesFactoryImpl(ClientRepository clientRepository, PricingPolicy pricingPolicy) {
        this.clientRepository = clientRepository;
        this.pricingPolicy = pricingPolicy;
    }

    @Override
    public BillingPolicy getPolicyFor(ServiceOrder serviceOrder) {
        Guid clientId = serviceOrder.getClientId();
        BillingPreferences billingPreferences = clientRepository.getBillingPreferences(clientId);
        switch(billingPreferences) {
            case SINGLE_SERVICE:
                return new SingleServicePolicy(pricingPolicy);
            case SERVICE_PER_SERVICE_ACTION:
                return new ServicePerActionPolicy(pricingPolicy);
            default:
                throw new IllegalStateException("Unkonwn billing prefernces!");
        }


    }
}
