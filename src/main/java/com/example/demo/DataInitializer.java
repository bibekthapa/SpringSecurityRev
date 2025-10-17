package com.example.demo;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if(userRepository.count() == 0) { // Only create admin if no users exist
            UserEntity admin = new UserEntity();

            String username = System.getenv().getOrDefault("ADMIN_USERNAME", "admin");
            String password = System.getenv().getOrDefault("ADMIN_PASSWORD", "admin123");

            admin.setUsername(username); // from env var
            admin.setPassword(passwordEncoder.encode(password));
            admin.setEnabled(true);

            RoleEntity role = new RoleEntity();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
            
            admin.setRoles(Set.of(role));

            userRepository.save(admin);
            System.out.println("Default admin created.");
        }
    }
}




