package com.itlibrium.cooldomain.model;

public interface ServiceActionRepository
{
    Money GetPricePerHourFor(Guid typeId);
}
