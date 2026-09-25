package com.radon.exception.types;

import com.radon.exception.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends ExceptionModel {
    public CategoryNotFoundException(String name) {
        super(
                "Category with name " + name + " not found!"
        );
    }
}
