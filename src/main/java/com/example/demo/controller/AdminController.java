package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    @PostMapping("register")
    public String register(@RequestBody User user){
        return "Registered";
    }
    
}
