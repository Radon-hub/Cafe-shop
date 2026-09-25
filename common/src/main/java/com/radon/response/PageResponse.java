package com.radon.response;

public record PageResponse<T>(
        T content,
        int pageNumber,
        int pageSize,
        int totalPages,
        int totalItems
) {
}
