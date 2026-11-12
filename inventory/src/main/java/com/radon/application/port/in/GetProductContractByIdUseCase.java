package com.radon.application.port.in;

import com.radon.contracts.dto.ProductContract;

public interface GetProductContractByIdUseCase {
    ProductContract getProductContractById(Long id);
}
