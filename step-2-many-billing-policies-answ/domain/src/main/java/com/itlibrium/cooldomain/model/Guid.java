package com.itlibrium.cooldomain.model;

import lombok.Data;

import java.time.Instant;
import java.util.Random;

@Data
public class Guid {

    public static Guid newGuid() {
        return new Guid("" + Math.random() * 1000000);
    }
    public static final Guid EMPTY = new Guid("EMPTY");

    private final String id;
}
