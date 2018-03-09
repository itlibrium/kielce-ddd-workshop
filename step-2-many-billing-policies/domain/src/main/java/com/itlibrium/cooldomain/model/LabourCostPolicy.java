package com.itlibrium.cooldomain.model;

import java.time.Duration;

public class LabourCostPolicy implements PricingPolicy {
    private final ServiceActionRepository serviceActionRepository;

    public LabourCostPolicy(ServiceActionRepository serviceActionRepository)
    {
        this.serviceActionRepository = serviceActionRepository;
    }

    public Money GetPricing(ServiceAction serviceAction) {
        Guid serviceActionTypeId = serviceAction.getTypeId();
        Duration duration = serviceAction.getDuration();
        Money pricePerHour = serviceActionRepository.getPricePerHourFor(serviceActionTypeId);
        return Money.multiply(pricePerHour, duration.toHours());
    }
}
