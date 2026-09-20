package com.radon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customer")
public class TestController {

    @GetMapping("Login")
    public String login(){
        return "Login";
    }

}
