package com.radon.contracts.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserContract(
        UUID id,
        String firstName,
        String lastName,
        String userName
) {
}
