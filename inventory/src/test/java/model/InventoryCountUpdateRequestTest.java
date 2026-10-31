package model;

import com.radon.exception.ExceptionModel;
import com.radon.presentation.dto.InventoryCountUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class InventoryCountUpdateRequestTest {
    @Test
    public void success(){
        InventoryCountUpdateRequest add = new InventoryCountUpdateRequest(0);
        InventoryCountUpdateRequest add2 = new InventoryCountUpdateRequest(0);
        Assertions.assertNotNull(add);
        Assertions.assertEquals(add,add2);

        log.info("[TEST] Success : {} | is Equal to -> {}", add,add2);
    }
    @Test
    public void fail(){
        ExceptionModel model = Assertions.assertThrows(ExceptionModel.class, ()->{
            new InventoryCountUpdateRequest(null);
        });

        log.info("[TEST] Fail : {}", model.getMessage());
    }
}