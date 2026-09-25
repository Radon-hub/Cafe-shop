package com.radon.exception.types;

import com.radon.exception.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InventoryNotFoundException extends ExceptionModel {
    public InventoryNotFoundException(Long productId) {
        super("Inventory not found for product id " + productId);
    }
}
