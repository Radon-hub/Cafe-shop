package com.radon.exception.types;

import com.radon.exception.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ProductOutOfStockException extends ExceptionModel {
    public ProductOutOfStockException(String productId) {
      super("Product " + productId + " is out of stock");
    }
}
