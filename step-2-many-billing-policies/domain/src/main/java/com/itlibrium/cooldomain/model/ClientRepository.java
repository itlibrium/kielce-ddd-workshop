package com.itlibrium.cooldomain.model;

public interface ClientRepository
{
    Client getById(Guid id);
    BillingPreferences getBillingPreferences(Guid clientId);
}