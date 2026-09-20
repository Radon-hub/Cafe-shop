package com.radon.response;


public record Response<T>(
        T data,
        ErrorResponse error
) {
    public Response(T data) {
        this(data, null);
    }
}
