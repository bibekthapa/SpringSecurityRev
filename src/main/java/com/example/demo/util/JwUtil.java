package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwUtil {

    private final String securekey = "securitysecurity1233343#@fgyY()@#$%";
    Key key = Keys.hmacShaKeyFor(securekey.getBytes(StandardCharsets.UTF_8));

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(securekey.getBytes());
    }

    public String generateToken(UserEntity userDetails){

        return Jwts.builder().
            subject(userDetails.getUsername()).
            issuedAt(new Date()).
            expiration(new Date(System.currentTimeMillis() + 1000* 60 *1)).
            signWith(key).compact();

    }

    public String generateRefreshToken(UserEntity user){
            return Jwts.builder().
            subject(user.getUsername()).
            issuedAt(new Date()).
            expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 7)).
            signWith(key).compact();
    }

    public String getUserName (String token){

        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    
    
    }

    public Claims validateToken(String token ){

        try {
        String editedToken = token.replace("Bearer ", "");
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(editedToken).getBody();

        return claims;
        }catch(JwtException e){
            throw new RuntimeException("Invalid JWT token : " + e.getMessage());
        }
    }


}
