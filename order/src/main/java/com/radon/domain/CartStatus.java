package com.radon.domain;

import java.io.Serializable;

public enum CartStatus implements Serializable {
    ACTIVE,
    CHECKED_OUT,
    ABANDONED
}
