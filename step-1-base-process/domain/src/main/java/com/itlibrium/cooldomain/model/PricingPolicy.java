package com.itlibrium.cooldomain.model;

public interface PricingPolicy
{
    Money GetPricing(ServiceAction serviceAction);
}
