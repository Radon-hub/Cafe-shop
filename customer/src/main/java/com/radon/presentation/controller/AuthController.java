package com.radon.presentation.controller;

import com.radon.application.port.in.RegisterUserUseCase;
import com.radon.domain.User;
import com.radon.presentation.dto.UserRegisterRequest;
import com.radon.presentation.dto.UserRegisterResponse;
import com.radon.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @GetMapping
    public String resooo(){
        return "Hello World!";
    }

    @PostMapping("register")
    public ResponseEntity<Response<UserRegisterResponse>> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {

        User newUser = registerUserUseCase.registerUser(User.of(userRegisterRequest));

        return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>(
                            new UserRegisterResponse(
                                    "Registered!",
                                    "Hello " + newUser.firstName() + ", " + "Welcome aboard..."
                            )
                    )
            );
    }
}
