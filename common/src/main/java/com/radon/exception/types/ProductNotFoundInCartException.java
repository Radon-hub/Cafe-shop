package com.radon.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundInCartException extends RuntimeException {
    public ProductNotFoundInCartException(Long productId) {
        super("Product "+ productId +" is not exist in Cart !");
    }
}
