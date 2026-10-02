package com.radon.presentation;

import com.radon.application.port.in.AddProductUseCase;
import com.radon.application.port.in.GetProductByIdUseCase;
import com.radon.application.port.in.UpdateProductUseCase;
import com.radon.domain.Product;
import com.radon.presentation.dto.ProductAddRequest;
import com.radon.presentation.dto.ProductResponse;
import com.radon.presentation.dto.ProductUpdateRequest;
import com.radon.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final GetProductByIdUseCase getProductByIdUseCase;
    private final AddProductUseCase addProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    public ProductController(GetProductByIdUseCase getProductByIdUseCase, AddProductUseCase addProductUseCase, UpdateProductUseCase updateProductUseCase) {
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.addProductUseCase = addProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
    }

    @PostMapping
    public ResponseEntity<Response<ProductResponse>> addProduct(@RequestBody ProductAddRequest product) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(
                        ProductResponse.of(
                                addProductUseCase.addNewProduct(Product.of(product))
                        )
                )
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<Product>> getAllProducts(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(getProductByIdUseCase.getProductById(id))
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<ProductResponse>> updateProduct(@PathVariable long id, @RequestBody ProductUpdateRequest product) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(
                        ProductResponse.of(
                                updateProductUseCase.updateProduct(
                                        Product.of(product).toBuilder()
                                                .id(id)
                                                .build()
                                )
                        )
                )
        );
    }
}
