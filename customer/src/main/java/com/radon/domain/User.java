package com.radon.domain;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record User(
        UUID id,
        String firstName,
        String lastName,
        String userName,
        String password,
        Set<Role> roles
) {
}
