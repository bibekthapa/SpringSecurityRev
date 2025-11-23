package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.util.JwUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Maps;
import lombok.Data;



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
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

        // need to return token in body
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        UserEntity userName = userService.getUser(authRequest.getUserName());
        String token = jwUtil.generateToken(userName);
        String refreshToken = jwUtil.generateRefreshToken(userName);
        return ResponseEntity.ok(new String[]{"accessToken: "+token,"refreshToken :" +refreshToken});
        
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String token){
        Claims claim = jwUtil.validateToken(token);
        String username = claim.getSubject();
        UserEntity userEntity = userService.getUser(username);
        String accessToken = jwUtil.generateToken(userEntity);
        Map<String, String> tokens = Map.of("refreshToken",token ,"accessToken" , accessToken);
        return ResponseEntity.ok(tokens);
    }

    @Data
    static class  AuthRequest{
        private String userName;
        private String password;
    }
    
    
}
