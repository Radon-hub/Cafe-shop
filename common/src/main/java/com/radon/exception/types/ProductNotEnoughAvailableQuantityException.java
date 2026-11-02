package com.radon.exception.types;

import com.radon.exception.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ProductNotEnoughAvailableQuantityException extends ExceptionModel {
    public ProductNotEnoughAvailableQuantityException(String productId) {
        super("Product " + productId + " quantity is not enough!");
    }
}
