package com.radon.exception.types;

import com.radon.exception.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryExistsException extends ExceptionModel {
    public CategoryExistsException(String name) {
        super("Category with name " + name + " already exists!");
    }
}
