package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(){
        return ResponseEntity.ok().body("Hi I am accessible by admin and user roles individually or joint ");
    }
}
