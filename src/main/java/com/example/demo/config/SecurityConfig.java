package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.JwtAuthFilter;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwUtil;

@Configuration
public class SecurityConfig {

    private final JwUtil util;
    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository , JwUtil util ){
        this.userRepository = userRepository;
        this.util = util;
      
    }

     @Bean
    public UserDetailsService userDetailsService(){
        return userName -> userRepository.findByUsername(userName)
        .map(user -> new User(user.getUsername(), user.getPassword(), user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName())).toList()
        )).orElseThrow(()-> new UsernameNotFoundException("User not found" ));
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(){
       return new JwtAuthFilter(util,userDetailsService());
    }


    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception{
        
        http.csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth.requestMatchers("/user/**").authenticated()
                                        .requestMatchers("/users/**").hasRole("USER")
                                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                        .requestMatchers("/public/**").hasAnyRole("ADMIN","USER")
                                        .requestMatchers("/api/login/**").permitAll()
                                        .anyRequest().authenticated()
                   
                    )
                    .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                    .httpBasic(basic -> basic.disable());
    

        return http.build();
    }

//    @Bean
// public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

//     UserDetails user = User.withUsername("bibek")
//             .password(passwordEncoder.encode("password"))
//             .roles("USER")
//             .build();

//     UserDetails admin = User.withUsername("admin")
//             .password(passwordEncoder.encode("admin123"))
//             .roles("ADMIN")
//             .build();

//     UserDetails manager = User.withUsername("manager")
//             .password(passwordEncoder.encode("manager123"))
//             .roles("MANAGER")
//             .build();

//     // InMemoryUserDetailsManager can take multiple users
//     return new InMemoryUserDetailsManager(user, admin, manager);
// }
   

    @Bean
    public PasswordEncoder bEncoder(){

        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    
}
