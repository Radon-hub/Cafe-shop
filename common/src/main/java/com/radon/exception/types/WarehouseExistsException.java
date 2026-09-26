package com.radon.exception.types;

import com.radon.exception.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WarehouseExistsException extends ExceptionModel {
    public WarehouseExistsException(String name) {
        super("Warehouse with name " + name + " already exists!");
    }
}
