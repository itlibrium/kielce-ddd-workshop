package com.itlibrium.cooldomain.model;

import java.util.Collection;

public class ServicePerActionPolicy implements BillingPolicy {

    @Override
    public Collection<Service> createServicesFor(ServiceOrder serviceOrder) {
        throw new UnsupportedOperationException();
    }
}
