package com.itlibrium.cooldomain.model;

public class OpenServiceOrderHandler {

    private final ServiceOrderRepository serviceOrderRepository;

    public OpenServiceOrderHandler(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    public Guid Handle(OpenServiceOrderCommand command) {
        ServiceOrder serviceOrder = ServiceOrder.openFor(command.getClientId());
        serviceOrderRepository.Save(serviceOrder);
        return serviceOrder.getId();

    }
}
