package com.radon.application.port.out;

import com.radon.domain.Category;
import com.radon.infrastructure.entity.CategoryEntity;

import java.util.List;

public interface CategoryRepository {
    Category addNewCategory(Category category);
    List<Category> searchCategory(String name);
    Category updateCategory(String oldName, String newName);
    String deleteCategory(Category category);
    CategoryEntity findCategoryById(Long id);
}
