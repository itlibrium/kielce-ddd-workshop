package com.itlibrium.cooldomain.model;

import lombok.Getter;

import java.util.Collection;

public class ServiceOrder
{
    @Getter
    private Guid clientId;

    public static ServiceOrder openFor(Guid clientId)
    {
        return new ServiceOrder(clientId, ServiceOrderStatus.OPEN);
    }

    private ServiceOrder(Guid clientId, ServiceOrderStatus status)
    {
        this.clientId = clientId;
        throw new UnsupportedOperationException();
    }

    public static ServiceOrder Restore(Guid id, Guid clientId, ServiceOrderStatus status, Collection<ServiceAction> serviceActions) {
        return new ServiceOrder(id, clientId, status, serviceActions);
    }

    private ServiceOrder(Guid id, Guid clientId, ServiceOrderStatus status, Collection<ServiceAction> serviceActions) {
        this.clientId = clientId;
        throw new UnsupportedOperationException();
    }


    public void AddServiceAction(ServiceAction serviceAction)
    {
        throw new UnsupportedOperationException();
    }

    public Collection<ServiceAction> GetServiceActions()
    {
        throw new UnsupportedOperationException();
    }

    public void Close()
    {
        throw new UnsupportedOperationException();
    }
}