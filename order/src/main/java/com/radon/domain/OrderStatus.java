package com.radon.domain;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    CREATED,
    PENDING_PAYMENT,
    PROCESSING,
    EXPIRED,
    CONFIRMED,
    CANCELLED,
    FAILED
}
