package com.radon.application.port.out;

import com.radon.domain.Product;
import com.radon.presentation.dto.ProductSearchFilterRequest;
import org.springframework.data.domain.Page;


public interface ProductRepository {
    Product getProductById(Long id);
    Product addNewProduct(Product product);
    Product updateProduct(Product product);
    Page<Product> searchProduct(ProductSearchFilterRequest filter, int page, int size);
}
