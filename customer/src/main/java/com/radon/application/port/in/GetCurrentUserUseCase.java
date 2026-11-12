package com.radon.application.port.in;

import com.radon.contracts.dto.UserContract;

public interface GetCurrentUserUseCase {
    UserContract getCurrentUser();
}
