package model;

import com.radon.exception.ExceptionModel;
import com.radon.presentation.dto.CategoryAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class CategoryAddRequestTest {
    @Test
    public void success(){
        CategoryAddRequest add = new CategoryAddRequest("category");
        Assertions.assertNotNull(add);
        log.info("[TEST] Success : {}", add);
    }
    @Test
    public void fail(){
        ExceptionModel exceptionModel = Assertions.assertThrows(ExceptionModel.class, ()->{
            new CategoryAddRequest(null);
        });
        log.info("[TEST] Fail : ", exceptionModel);
    }
}