package model;

import com.radon.exception.ExceptionModel;
import com.radon.presentation.dto.InventoryUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class InventoryUpdateRequestTest {
    @Test
    public void success(){
        InventoryUpdateRequest add = new InventoryUpdateRequest(1L,0,0L);
        InventoryUpdateRequest add2 = InventoryUpdateRequest.builder()
                .productId(1L)
                .wareHouseId(0L)
                .count(0)
                .build();
        Assertions.assertNotNull(add);
        Assertions.assertEquals(add,add2);

        log.info("[TEST] Success : {} | is Equal to -> {}", add,add2);
    }
    @Test
    public void fail(){
        ExceptionModel model = Assertions.assertThrows(ExceptionModel.class, ()->{
            new InventoryUpdateRequest(null,null,null);
        });

        log.info("[TEST] Fail : {}", model.getMessage());
    }
}