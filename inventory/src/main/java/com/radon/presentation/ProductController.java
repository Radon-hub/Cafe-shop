package com.radon.presentation;

import com.radon.application.port.in.AddProductUseCase;
import com.radon.application.port.in.GetProductByIdUseCase;
import com.radon.application.port.in.SearchProductUseCase;
import com.radon.application.port.in.UpdateProductUseCase;
import com.radon.domain.Product;
import com.radon.presentation.dto.ProductAddRequest;
import com.radon.presentation.dto.ProductResponse;
import com.radon.presentation.dto.ProductSearchFilterRequest;
import com.radon.presentation.dto.ProductUpdateRequest;
import com.radon.response.PageResponse;
import com.radon.response.Response;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final GetProductByIdUseCase getProductByIdUseCase;
    private final AddProductUseCase addProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final SearchProductUseCase searchProductUseCase;

    public ProductController(GetProductByIdUseCase getProductByIdUseCase, AddProductUseCase addProductUseCase, UpdateProductUseCase updateProductUseCase, SearchProductUseCase searchProductUseCase) {
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.addProductUseCase = addProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.searchProductUseCase = searchProductUseCase;
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

    @GetMapping("search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PageResponse<List<ProductResponse>>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) BigDecimal minWeight,
            @RequestParam(required = false) BigDecimal maxWeight,
            @RequestParam int page,
            @RequestParam int size
    ){

        val paged = searchProductUseCase.searchProduct(
                ProductSearchFilterRequest.builder()
                        .name(name)
                        .category(categories)
                        .minPrice(minPrice)
                        .maxPrice(maxPrice)
                        .minWeight(minWeight)
                        .maxWeight(maxWeight)
                        .build(),
                page,
                size
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new PageResponse<>(
                        paged.stream().map(ProductResponse::of).toList(),
                        paged.getNumber(),
                        paged.getSize(),
                        paged.getTotalPages(),
                        (int) paged.getTotalElements()
                )
        );
    }

}
