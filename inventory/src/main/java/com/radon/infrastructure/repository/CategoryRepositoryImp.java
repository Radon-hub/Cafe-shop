package com.radon.infrastructure.repository;

import com.radon.application.port.out.CategoryRepository;
import com.radon.domain.Category;
import com.radon.exception.types.CategoryExistsException;
import com.radon.exception.types.CategoryNotFoundException;
import com.radon.infrastructure.entity.CategoryEntity;
import com.radon.infrastructure.jpa.CategoryJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CategoryRepositoryImp implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryRepositoryImp(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Category addNewCategory(Category category) {

        Optional<CategoryEntity> existed = categoryJpaRepository.findByName(category.name());

        if(existed.isPresent()){
            throw new CategoryExistsException(category.name());
        }

        return Category.of(categoryJpaRepository.save(CategoryEntity.of(category)));

    }

    @Override
    public List<Category> searchCategory(String name) {
        return categoryJpaRepository.findAllByName(name).stream().map(Category::of).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Category updateCategory(String oldName, String newName) {

        CategoryEntity existed = categoryJpaRepository.findByName(oldName).orElseThrow(() -> new CategoryNotFoundException(oldName));

        if (categoryJpaRepository.findByName(newName).isPresent()){
            throw new CategoryExistsException(newName);
        }

        existed.setName(newName);

        return Category.of(categoryJpaRepository.save(existed));

    }

    @Override
    public String deleteCategory(Category category) {
        CategoryEntity existed = categoryJpaRepository.findByIdAndName(category.id(),category.name()).orElseThrow(
                () -> new CategoryNotFoundException(category.name() +" or id " +  category.id())
        );

        categoryJpaRepository.delete(existed);

        return "Category has been deleted.";
    }
}
