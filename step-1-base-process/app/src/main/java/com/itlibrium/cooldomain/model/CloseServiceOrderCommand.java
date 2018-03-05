package com.itlibrium.cooldomain.model;

import lombok.Data;

@Data
public class CloseServiceOrderCommand
{
    private Guid ServiceOrderId;
    private int PaymentType;
}
