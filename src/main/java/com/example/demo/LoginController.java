package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/test")
public class LoginController {
    
    @GetMapping("/login")
    public String getMethodName() {
        return "In the login endpoint";
    }
    
    
}
