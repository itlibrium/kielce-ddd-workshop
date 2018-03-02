package com.itlibrium.cooldomain.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;

public class ServiceOrder
{
    public Guid clientId;

    public static ServiceOrder openFor(Guid clientId)
    {
        return new ServiceOrder(clientId, ServiceOrderStatus.Open);
    }

    private ServiceOrder(Guid clientId, ServiceOrderStatus status)
    {
        this.clientId = clientId;
        throw new NotImplementedException();
    }

    public static ServiceOrder Restore(Guid id, Guid clientId, ServiceOrderStatus status, Collection<ServiceAction> serviceActions) {
        return new ServiceOrder(id, clientId, status, serviceActions);
    }

    private ServiceOrder(Guid id, Guid clientId, ServiceOrderStatus status, Collection<ServiceAction> serviceActions) {
        this.clientId = clientId;
        throw new NotImplementedException();
    }


    public void AddServiceAction(ServiceAction serviceAction)
    {
        throw new NotImplementedException();
    }

    public Collection<ServiceAction> GetServiceActions()
    {
        throw new NotImplementedException();
    }

    public void Close()
    {
        throw new NotImplementedException();
    }
}