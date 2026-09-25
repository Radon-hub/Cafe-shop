package com.radon.application.port.out;

import com.radon.domain.Category;

import java.util.List;

public interface CategoryRepository {
    Category addNewCategory(Category category);
    List<Category> searchCategory(String name);
    Category updateCategory(String oldName, String newName);
    String deleteCategory(Category category);
}
