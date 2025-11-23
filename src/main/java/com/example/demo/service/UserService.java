package com.example.demo.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    public UserService(UserRepository userRepository , PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity registerUser(UserDTO userDTO ){

        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new ApiException("Username Already Exists", HttpStatus.CONFLICT);

        }

     //need to set roleEntities
        Set<RoleEntity> roleEntities = userDTO.getRoles().stream().map(
            roleDTO -> roleRepository.findByName(roleDTO.getName()).orElseGet(()->{

            RoleEntity newRole = new RoleEntity();
            newRole.setName(roleDTO.getName());
            return roleRepository.save(newRole);
            
            })
        ).collect(Collectors.toSet());

          UserEntity userEntity = new UserEntity();

        
            
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setEnabled(true);
        userEntity.setRoles(roleEntities);
        userRepository.save(userEntity);
        return userEntity;

    }


    public UserEntity getUser(String userName){
      
        UserEntity user = userRepository.findByUsername(userName).orElseThrow(()->new ApiException("User not in the system ", HttpStatus.NOT_FOUND));
        return user;
    }

    public UserDetails loadUser(String userName){

        UserEntity user = userRepository.findByUsername(userName).orElseThrow(()-> new ApiException("User not found in the system", HttpStatus.NOT_FOUND));

        return User.builder().username(user.getUsername()).password(user.getPassword())
        .authorities
        (user.getRoles().
        stream().
        map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList()))
        .build();

    }
    
    
}
