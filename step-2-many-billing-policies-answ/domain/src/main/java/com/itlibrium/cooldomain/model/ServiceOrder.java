package com.itlibrium.cooldomain.model;

import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ServiceOrder
{

    @Getter
    private final Guid id;

    @Getter
    private final Guid clientId;

    private Map<Guid, ServiceAction> serviceActionsMap = new HashMap<>();

    private ServiceOrderStatus status;

    public static ServiceOrder openFor(Guid clientId)
    {
        return new ServiceOrder(clientId, ServiceOrderStatus.OPEN);
    }

    private ServiceOrder(Guid clientId) {
        this(clientId, ServiceOrderStatus.OPEN);
    }

    private ServiceOrder(Guid clientId, ServiceOrderStatus status) {
        this.id = Guid.newGuid();
        this.clientId = clientId;
        this.status = status;
    }

    public static ServiceOrder restore(Guid id, Guid clientId, ServiceOrderStatus status, Collection<ServiceAction> serviceActions) {
        return new ServiceOrder(id, clientId, status, serviceActions);
    }

    private ServiceOrder(Guid id, Guid clientId, ServiceOrderStatus status, Collection<ServiceAction> serviceActions) {
        this.id = id;
        this.clientId = clientId;
        this.status = status;
        serviceActions.forEach( sa -> serviceActionsMap.put(sa.getTypeId(), sa));
    }


    public void addServiceAction(ServiceAction serviceAction)
    {
        if (status == ServiceOrderStatus.CLOSED)
            throw new BusinessException();

        if (serviceActionsMap.containsKey(serviceAction.getTypeId()))
            throw new BusinessException();

        serviceActionsMap.put(serviceAction.getTypeId(), serviceAction);
    }

    public Collection<ServiceAction> getServiceActions()
    {
        return serviceActionsMap.values();
    }

    public void close()
    {
        status = ServiceOrderStatus.CLOSED;
    }
}