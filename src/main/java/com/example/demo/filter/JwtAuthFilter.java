package com.example.demo.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.util.JwUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

  

    private final JwUtil util;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwUtil util, UserDetailsService userDetailsService) {
        this.util = util;
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                final String path = request.getServletPath();

                if(path.startsWith("/api/login")){
                    filterChain.doFilter(request, response);
                }
       
                final String authHeader = request.getHeader("Authorization");

                String userName = "" ,jwtToken = "";

                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    jwtToken = authHeader.substring(7);
                    userName = util.getUserName(jwtToken);
                }

                if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){

                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

                filterChain.doFilter(request, response);
    }
    
}
