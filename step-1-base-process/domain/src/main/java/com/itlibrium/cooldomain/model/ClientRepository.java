package com.itlibrium.cooldomain.model;

public interface ClientRepository
    {
        Client GetById(Guid id);
    }