package com.itlibrium.cooldomain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddServiceActionCommand
{
    private Guid serviceOrderId ;
    private Guid typeId ;
    private LocalDateTime start;
    private LocalDateTime end;

}
