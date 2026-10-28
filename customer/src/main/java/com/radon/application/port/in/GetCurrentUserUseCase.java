package com.radon.application.port.in;

import com.radon.contracts.UserContract;

public interface GetCurrentUserUseCase {
    UserContract getCurrentUser();
}
