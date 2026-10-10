package com.radon.domain;

public enum TokenType {

    ACCESS(600),
    REFRESH(3600);

    private final int expiration;

    public int getExpiration() {
        return expiration;
    }

    TokenType(int expiration) {
        this.expiration = expiration;
    }
}
