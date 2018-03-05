package com.itlibrium.cooldomain.model

import spock.lang.Specification

class ClientTest extends Specification {

    private Money _deferredPaymentLimit
    private boolean _isVip
    private Boolean _isPaymentAccepted

    def "Payment conditions are checked"() {
        given:
            vipStatus(isVip)
            deferredPaymentLimit(dfrdPayLimit)
        when:
            checkIfCanPay(paymentType, price)
        then:
            resultOfCheckingIfCanPayIs(result)
        where:
           isVip | dfrdPayLimit |      paymentType      | price | result
           false |           0  | PaymentType.IMMEDIATE | 100   | true
           true  |           0  | PaymentType.DEFERRED  | 100   | true
           false |         100  | PaymentType.DEFERRED  | 10    | true
           false |         100  | PaymentType.DEFERRED  | 100   | true
           false |         100  | PaymentType.DEFERRED  | 101   | false
    }

    private void deferredPaymentLimit(double value) {
        _deferredPaymentLimit = Money.fromDouble(value)
    }

    private void vipStatus(boolean isVip) {
        _isVip = isVip;
    }

    private void checkIfCanPay(PaymentType paymentType, double price)
    {
        Client client = restoreClient();
        _isPaymentAccepted = client.canPay(Money.fromDouble(price), paymentType);
    }

    private Client restoreClient() {
        Client.restore(Guid.EMPTY, _deferredPaymentLimit, _isVip);
    }

    private boolean resultOfCheckingIfCanPayIs(boolean result) {
        _isPaymentAccepted == result
    }

}
