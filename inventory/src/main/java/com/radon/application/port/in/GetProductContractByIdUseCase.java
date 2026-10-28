package com.radon.application.port.in;

import com.radon.contracts.ProductContract;

public interface GetProductContractByIdUseCase {
    ProductContract getProductContractById(Long id);
}
