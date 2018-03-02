package com.itlibrium.cooldomain.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Client
{
    public static Client Create(Money deferredPaymentLimit, boolean isVip) {
        return new Client(Guid.newGuid(), deferredPaymentLimit, isVip);
    }

    public static Client Restore(Guid id, Money deferredPaymentLimit, boolean isVip) {
        return new Client(id, deferredPaymentLimit, isVip);
    }

    private Client (Guid id, Money deferredPaymentLimit, boolean isVip)
    {
        throw new NotImplementedException();
    }

    public boolean canPay(Money requestedValue, PaymentType paymentType)
    {
        throw new NotImplementedException();
    }

    //Przypisywanie limitu płatności odroczonych
    //Ustawianie statusu klienta
    //...
}
