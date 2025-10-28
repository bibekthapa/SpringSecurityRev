package com.example.demo.util;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwUtil {

    private final String securekey = "securitysecurity1233343#@fgyY()@#$%";
    Key key = Keys.hmacShaKeyFor(securekey.getBytes());

    public String generateToken(UserEntity userDetails){

        return Jwts.builder().
            subject(userDetails.getUsername()).
            issuedAt(new Date()).
            expiration(new Date(System.currentTimeMillis() + 1000* 60 *60)).
            signWith(key).compact();

    }


}
