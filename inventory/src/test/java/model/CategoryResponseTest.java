package model;

import com.radon.domain.Category;
import com.radon.presentation.dto.CategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class CategoryResponseTest {

    Category category = Category.builder()
            .id(1L)
            .name("Category 1")
            .build();

    @Test
    void of_success_response() {
        CategoryResponse response = CategoryResponse.of(category);

        Assertions.assertEquals(category.name(), response.name());

        log.info("[TEST] Success : {} | is Equal to -> {}", category,response);

    }

}