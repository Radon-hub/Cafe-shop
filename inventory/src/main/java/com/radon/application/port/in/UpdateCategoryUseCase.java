package com.radon.application.port.in;

import com.radon.domain.Category;

public interface UpdateCategoryUseCase {
    Category updateCategory(String oldName, String newName);
}
