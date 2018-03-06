package com.itlibrium.cooldomain.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Guid {

    public static Guid newGuid() {
        return new Guid(Instant.now().toString());
    }
    public static final Guid EMPTY = new Guid("EMPTY");

    private final String id;
}
