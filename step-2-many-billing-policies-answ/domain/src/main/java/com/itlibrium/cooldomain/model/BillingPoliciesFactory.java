package com.itlibrium.cooldomain.model;

interface BillingPoliciesFactory {
    BillingPolicy getPolicyFor(ServiceOrder serviceOrder);
}
