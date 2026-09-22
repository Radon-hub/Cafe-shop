package com.radon.exception.types;

import com.radon.exception.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductExistsException extends ExceptionModel {
    public ProductExistsException(Long productId) {
        super("Product with id " + productId + " already exists!");
    }
}
