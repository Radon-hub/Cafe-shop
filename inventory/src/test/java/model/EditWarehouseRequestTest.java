package model;

import com.radon.presentation.dto.EditWarehouseRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class EditWarehouseRequestTest {

    @Test
    void builder() {
        EditWarehouseRequest editWarehouseRequest = new EditWarehouseRequest(
                1L,"Warehouse"
        );
        Assertions.assertNotNull(editWarehouseRequest);
        Assertions.assertEquals(1L,editWarehouseRequest.id());
        log.info("[TEST] Success : {} | is Equal to -> {}", editWarehouseRequest.name(),"Warehouse");
    }
}