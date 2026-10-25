package com.radon.application.service;

import com.radon.application.port.in.CreateCartUseCase;
import com.radon.contracts.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterListener {

    private final CreateCartUseCase createCartUseCase;

    public UserRegisterListener(CreateCartUseCase createCartUseCase) {
        this.createCartUseCase = createCartUseCase;
    }

    @EventListener
    void onUserRegisterEvent(UserRegisterEvent userRegisterEvent) {
        createCartUseCase.createCart(userRegisterEvent.id());
    }

}
