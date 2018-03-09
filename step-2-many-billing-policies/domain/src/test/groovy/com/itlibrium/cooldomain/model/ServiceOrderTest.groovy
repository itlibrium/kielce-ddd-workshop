package com.itlibrium.cooldomain.model

import spock.lang.Specification

import java.time.Duration

class ServiceOrderTest extends Specification {

    private ServiceOrderStatus _status
    private ServiceOrder _serviceOrder


    def "Can add service action for not closed service order"() {
        given:
            serviceOrderStatusIs(ServiceOrderStatus.OPEN)
        when:
            addServiceAction()
        then:
            serviceActionIsAdded()
    }

    def "Can not add service action"() {
        given:
            serviceOrderStatusIs(ServiceOrderStatus.CLOSED)
        when:
            addServiceAction()
        then:
            thrown BusinessException
    }

    private void serviceOrderStatusIs(ServiceOrderStatus status) { _status = status }

    private void addServiceAction()
    {
        _serviceOrder = restoreServiceOrder()
        _serviceOrder.addServiceAction(new ServiceAction(Guid.newGuid(), Duration.ZERO))
    }

    private ServiceOrder restoreServiceOrder() {
        ServiceOrder.restore(Guid.EMPTY, Guid.EMPTY, _status, [])
    }

    private void serviceActionIsAdded() { _serviceOrder.getServiceActions().size() == 1 }

}
