package com.itlibrium.cooldomain.model;

import java.util.Collection;

@FunctionalInterface
public interface BillingPolicy
{
    Collection<Service> createServicesFor(ServiceOrder serviceOrder);
}
