package com.radon.presentation;

import com.radon.application.port.in.GetProductByIdUseCase;
import com.radon.domain.Product;
import com.radon.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final GetProductByIdUseCase getProductByIdUseCase;

    public ProductController(GetProductByIdUseCase getProductByIdUseCase) {
        this.getProductByIdUseCase = getProductByIdUseCase;
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<Product>> getAllProducts(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(getProductByIdUseCase.getProductById(id))
        );
    }
}
