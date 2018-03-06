package com.itlibrium.cooldomain.model;

import java.time.Duration;

public class AddServiceActionHandler {

    private final ServiceOrderRepository serviceOrderRepository;

    public AddServiceActionHandler(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }


    public void handle(AddServiceActionCommand command) {
        ServiceOrder serviceOrder = serviceOrderRepository.GetById(command.getServiceOrderId());
        serviceOrder.addServiceAction(new ServiceAction(
                command.getTypeId(),
                Duration.between(command.getStart(), command.getEnd())));

        serviceOrderRepository.Save(serviceOrder);
    }
}
