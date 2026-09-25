package com.radon.presentation;

import com.radon.application.port.in.AddCategoryUseCase;
import com.radon.application.port.in.DeleteCategoryUseCase;
import com.radon.application.port.in.SearchCategoryUseCase;
import com.radon.application.port.in.UpdateCategoryUseCase;
import com.radon.domain.Category;
import com.radon.presentation.dto.CategoryAddRequest;
import com.radon.presentation.dto.CategoryDeleteRequest;
import com.radon.presentation.dto.CategoryResponse;
import com.radon.presentation.dto.CategoryUpdateRequest;
import com.radon.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private final AddCategoryUseCase addCategoryUseCase;
    private final SearchCategoryUseCase searchCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    public CategoryController(AddCategoryUseCase addCategoryUseCase, SearchCategoryUseCase searchCategoryUseCase, UpdateCategoryUseCase updateCategoryUseCase, DeleteCategoryUseCase deleteCategoryUseCase) {
        this.addCategoryUseCase = addCategoryUseCase;
        this.searchCategoryUseCase = searchCategoryUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
    }

    @GetMapping
    public ResponseEntity<Response<List<CategoryResponse>>> searchCategory(@RequestParam String name){
        return ResponseEntity.ok(
                new Response<>(
                        searchCategoryUseCase.searchCategory(name).stream().map(CategoryResponse::of).toList()
                )
        );
    }

    @PostMapping
    public ResponseEntity<Response<CategoryResponse>> addNewCategory(@RequestBody CategoryAddRequest categoryAddRequest) {
        return ResponseEntity.ok(
                new Response<>(
                        CategoryResponse.of(
                                addCategoryUseCase.addNewCategory(Category.of(categoryAddRequest))
                        )
                )
        );
    }

    @PutMapping()
    public ResponseEntity<Response<CategoryResponse>> updateCategory(@RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        return ResponseEntity.ok(
                new Response<>(
                        CategoryResponse.of(
                                updateCategoryUseCase.updateCategory(categoryUpdateRequest.oldName(),categoryUpdateRequest.newName())
                        )
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<Response<String>> deleteCategory(@RequestBody CategoryDeleteRequest categoryDeleteRequest) {
        return ResponseEntity.ok(
                new Response<>(
                        deleteCategoryUseCase.deleteCategory(Category.of(categoryDeleteRequest))
                )
        );
    }

}
