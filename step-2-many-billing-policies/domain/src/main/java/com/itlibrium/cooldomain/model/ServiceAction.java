package com.itlibrium.cooldomain.model;

import lombok.Value;

import java.time.Duration;

@Value
class ServiceAction
{
    private final Guid typeId;
    private final Duration duration;

}