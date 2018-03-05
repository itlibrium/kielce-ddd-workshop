package com.itlibrium.cooldomain.model;

import lombok.Data;

import java.time.Duration;

@Data
class ServiceAction
{
    private Guid typeId;
    private Duration duration;

}