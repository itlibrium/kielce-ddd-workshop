package com.itlibrium.cooldomain.model;

import java.util.Collection;
import java.util.stream.Collectors;

public class ServicePerActionPolicy implements BillingPolicy {

    private final PricingPolicy pricingPolicy;

    public ServicePerActionPolicy(PricingPolicy pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }

    @Override
    public Collection<Service> createServicesFor(ServiceOrder serviceOrder) {
        return serviceOrder.getServiceActions().stream().map(pricingPolicy::GetPricing)
                .map(price -> Service.create(serviceOrder.getId(), price)).collect(Collectors.toList());
    }
}
