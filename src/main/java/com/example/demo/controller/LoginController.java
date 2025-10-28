package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.util.JwUtil;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/")
public class LoginController {
    
    @Autowired
    private JwUtil jwUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {

        // need to return token in body
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        UserEntity userName = userService.getUser(authRequest.getUserName());
        String token = jwUtil.generateToken(userName);
        return ResponseEntity.ok(token);
        
    }

    @Data
    static class  AuthRequest{
        private String userName;
        private String password;
    }
    
    
}
