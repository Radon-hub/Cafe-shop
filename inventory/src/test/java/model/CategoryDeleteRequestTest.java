package model;

import com.radon.exception.ExceptionModel;
import com.radon.presentation.dto.CategoryDeleteRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CategoryDeleteRequestTest {
    @Test
    public void success(){
        CategoryDeleteRequest categoryDeleteRequest = new CategoryDeleteRequest(1L,"category");
        Assertions.assertNotNull(categoryDeleteRequest);
        log.info("[TEST] Success : {}", categoryDeleteRequest);
    }
    @Test
    public void fail(){
        ExceptionModel exceptionModel = Assertions.assertThrows(ExceptionModel.class, ()->{
            new CategoryDeleteRequest(null,null);
        });
        log.info("[TEST] Fail : ", exceptionModel);
    }
}
