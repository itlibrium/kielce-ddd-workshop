package com.itlibrium.cooldomain.model;

import lombok.Data;

@Data
public class CloseServiceOrderCommand
{
    private final Guid ServiceOrderId;
    private final int PaymentType;
}
