package com.itlibrium.cooldomain.model;

import lombok.Value;

@Value
public class Client {

    private final Guid id;
    private final Money deferredPaymentLimit;
    private final boolean isVip;

    public static Client create(Money deferredPaymentLimit, boolean isVip) {
        return new Client(Guid.newGuid(), deferredPaymentLimit, isVip);
    }

    public static Client restore(Guid id, Money deferredPaymentLimit, boolean isVip) {
        return new Client(id, deferredPaymentLimit, isVip);
    }

    public boolean canPay(Money requestedValue, PaymentType paymentType) {

        switch (paymentType)
        {
            case DEFERRED:
                return isVip || requestedValue.lessThan(deferredPaymentLimit) || requestedValue.equals(deferredPaymentLimit);
            case IMMEDIATE:
                return true;
            default:
                throw new IllegalArgumentException(paymentType.name());
        }
    }

    //Przypisywanie limitu płatności odroczonych
    //Ustawianie statusu klienta
    //...
}
