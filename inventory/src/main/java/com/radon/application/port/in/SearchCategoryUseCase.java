package com.radon.application.port.in;

import com.radon.domain.Category;

import java.util.List;

public interface SearchCategoryUseCase {
    List<Category> searchCategory(String name);
}
