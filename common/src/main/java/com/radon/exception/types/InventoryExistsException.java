package com.radon.exception.types;

import com.radon.exception.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InventoryExistsException extends ExceptionModel {
    public InventoryExistsException(Long productId) {
        super("Inventory already exists for product id " + productId);
    }
}
