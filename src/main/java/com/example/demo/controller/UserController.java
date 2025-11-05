package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    @GetMapping("/get")
    public ResponseEntity<?> getUser(){
        return ResponseEntity.ok().body("I am from users get endpoint");
    }
    
}
