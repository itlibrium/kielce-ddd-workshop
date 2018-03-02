package com.itlibrium.cooldomain.model;


public class Service
{
    private  Guid serviceOrderId;

    public Money price;

    public static Service create(Guid serviceOrderId, Money price) {
        return new Service(serviceOrderId, price);
    }

    private Service(Guid serviceOrderId, Money price)
    {
        this.serviceOrderId = serviceOrderId;
        this.price = price;
    }

    public static Service restore(Guid id, Guid serviceOrderId, Money price) {
        return new Service(id, serviceOrderId, price);
    }

    private Service(Guid id, Guid serviceOrderId, Money price)
    {
        this.serviceOrderId = serviceOrderId;
        this.price = price;
    }

    //Zmiana wyceny
    //Akceptacja wyceny
    //etc.
}
