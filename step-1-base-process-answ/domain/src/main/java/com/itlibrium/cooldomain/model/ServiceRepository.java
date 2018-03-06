package com.itlibrium.cooldomain.model;

import java.util.Collection;

public interface ServiceRepository
{

    Collection<Service> getAll();
    void save(Service service);
}
