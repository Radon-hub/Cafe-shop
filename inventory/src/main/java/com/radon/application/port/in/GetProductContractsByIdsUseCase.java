package com.radon.application.port.in;

import com.radon.contracts.dto.ProductContract;
import java.util.List;

public interface GetProductContractsByIdsUseCase {
    List<ProductContract> getProductContractsByIds(List<Long> id);
}
