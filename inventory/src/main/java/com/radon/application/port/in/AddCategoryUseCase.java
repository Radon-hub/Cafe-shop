package com.radon.application.port.in;

import com.radon.domain.Category;

public interface AddCategoryUseCase {
    Category addNewCategory(Category category);
}
