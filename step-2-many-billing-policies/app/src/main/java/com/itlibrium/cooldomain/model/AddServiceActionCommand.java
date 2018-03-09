package com.itlibrium.cooldomain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddServiceActionCommand
{
    private final Guid serviceOrderId ;
    private final Guid typeId ;
    private final LocalDateTime start;
    private final LocalDateTime end;

}
