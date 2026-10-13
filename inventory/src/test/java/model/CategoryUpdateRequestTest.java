package model;

import com.radon.exception.ExceptionModel;
import com.radon.exception.types.IllegalStateException;
import com.radon.presentation.dto.CategoryUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


@Slf4j
class CategoryUpdateRequestTest {
    @Test
    public void success(){
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest("old", "new");
        Assertions.assertNotNull(categoryUpdateRequest);
        log.info("[TEST] Success : {}", categoryUpdateRequest);
    }
    @Test
    public void fail_on_null_inputs(){
        ExceptionModel exceptionModel = Assertions.assertThrows(ExceptionModel.class, ()->{
            new CategoryUpdateRequest(null, null);
        });
        log.info("[TEST] Fail : ", exceptionModel);
    }
    @Test
    public void fail_on_same_inputs(){
        ExceptionModel exceptionModel = Assertions.assertThrows(ExceptionModel.class, ()->{
            new CategoryUpdateRequest("Ali", "Ali");
        });

        log.info("[TEST] Fail : ", exceptionModel);
    }
}