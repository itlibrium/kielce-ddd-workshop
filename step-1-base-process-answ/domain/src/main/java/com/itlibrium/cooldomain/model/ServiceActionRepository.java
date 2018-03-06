package com.itlibrium.cooldomain.model;

public interface ServiceActionRepository
{
    Money getPricePerHourFor(Guid typeId);
}
