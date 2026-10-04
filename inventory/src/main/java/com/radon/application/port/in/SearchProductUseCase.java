package com.radon.application.port.in;

import com.radon.domain.Product;
import com.radon.presentation.dto.ProductSearchFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface SearchProductUseCase {
    Page<Product> searchProduct(ProductSearchFilterRequest filter, int page, int size);
}
