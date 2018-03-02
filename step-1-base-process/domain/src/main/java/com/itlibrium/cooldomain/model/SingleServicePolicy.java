package com.itlibrium.cooldomain.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;

public class SingleServicePolicy implements BillingPolicy
{
    public Collection<Service> createServicesFor(ServiceOrder serviceOrder)
    {
        throw new NotImplementedException();
    }
}
