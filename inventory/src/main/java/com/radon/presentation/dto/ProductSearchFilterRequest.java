package com.radon.presentation.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductSearchFilterRequest(
        String name,
        List<String> category,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        BigDecimal minWeight,
        BigDecimal maxWeight
) {
}
