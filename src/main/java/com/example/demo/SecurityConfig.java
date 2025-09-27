package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception{
        
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/test/**").authenticated()
                   
                    ).formLogin(Customizer.withDefaults());
    

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){

        UserDetails user = User.withUsername("bibek")
                                .password(passwordEncoder.encode("password"))
                                .roles("USER")
                                .build();

        return new InMemoryUserDetailsManager(user);

    }

    @Bean
    public PasswordEncoder bEncoder(){

        return new BCryptPasswordEncoder();

    }
    
}
