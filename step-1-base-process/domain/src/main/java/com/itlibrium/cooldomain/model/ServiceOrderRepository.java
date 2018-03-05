package com.itlibrium.cooldomain.model;

public interface ServiceOrderRepository
{
    ServiceOrder GetById(Guid serviceOrderId);
    void Save(ServiceOrder serviceOrder);
}
