package com.radon.application.service;

import com.radon.application.port.in.AddCategoryUseCase;
import com.radon.application.port.in.DeleteCategoryUseCase;
import com.radon.application.port.in.SearchCategoryUseCase;
import com.radon.application.port.in.UpdateCategoryUseCase;
import com.radon.application.port.out.CategoryRepository;
import com.radon.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements AddCategoryUseCase, SearchCategoryUseCase, UpdateCategoryUseCase, DeleteCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category addNewCategory(Category category) {
        return categoryRepository.addNewCategory(category);
    }

    @Override
    public List<Category> searchCategory(String name) {
        return categoryRepository.searchCategory(name);
    }

    @Override
    public Category updateCategory(String oldName, String newName) {
        return categoryRepository.updateCategory(oldName,newName);
    }

    @Override
    public String deleteCategory(Category category) {
        return categoryRepository.deleteCategory(category);
    }
}
