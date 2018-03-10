package com.itlibrium.cooldomain.model;

import java.util.Collection;
import java.util.Collections;

public class SingleServicePolicy implements BillingPolicy {

    private final PricingPolicy pricingPolicy;

    public SingleServicePolicy(PricingPolicy pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }

    public Collection<Service> createServicesFor(ServiceOrder serviceOrder)
    {
        Money totalPrice = serviceOrder.getServiceActions().stream().map(pricingPolicy::GetPricing).reduce(Money.ZERO, Money::sum);
        Service singleService = Service.create(serviceOrder.getId(), totalPrice);

        return Collections.singletonList(singleService);
    }
}
