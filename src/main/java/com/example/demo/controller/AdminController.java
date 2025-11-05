package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/admin/")
public class AdminController {


    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user){
        
        userService.registerUser(user);
        return ResponseEntity.ok().header("Custom-Header", "hi").body(user);

    }

    
    
}
