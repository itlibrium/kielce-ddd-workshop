package com.itlibrium.cooldomain.model;

import java.util.Collection;

public class SingleServicePolicy implements BillingPolicy
{
    public Collection<Service> createServicesFor(ServiceOrder serviceOrder)
    {
        throw new UnsupportedOperationException();
    }
}
