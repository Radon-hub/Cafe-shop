package com.radon.presentation.controller;

import com.radon.application.port.in.LoginUserUseCase;
import com.radon.application.port.in.RefreshTokenUseCase;
import com.radon.application.port.in.RegisterUserUseCase;
import com.radon.domain.JwtTokens;
import com.radon.domain.User;
import com.radon.presentation.dto.*;
import com.radon.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUserUseCase loginUserUseCase, RefreshTokenUseCase refreshTokenUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    @PostMapping("login")
    public ResponseEntity<Response<UserLoginResponse>> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        JwtTokens tokens = loginUserUseCase.loginUser(userLoginRequest.userName(), userLoginRequest.password());
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(
                        new UserLoginResponse(
                                tokens.accessToken(),
                                tokens.refreshToken()
                        )
                )
        );
    }

    @PostMapping("refresh")
    public ResponseEntity<Response<UserLoginResponse>> refresh(@RequestBody UserRefreshRequest userRefreshRequest) {
        JwtTokens tokens = refreshTokenUseCase.refreshToken(userRefreshRequest.refreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(
                        new UserLoginResponse(
                                tokens.accessToken(),
                                tokens.refreshToken()
                        )
                )
        );
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
