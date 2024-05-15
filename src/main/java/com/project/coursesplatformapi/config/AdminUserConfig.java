package com.project.coursesplatformapi.config;

import com.project.coursesplatformapi.dto.UserDTO;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.model.enums.Role;
import com.project.coursesplatformapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {

        userRepository.findByUsername("admin").ifPresentOrElse(user -> System.out.println("Admin user already exists"), () -> {
            var userDTO = new UserDTO("admin", "admin", "admin@email.com", passwordEncoder.encode("admin"), Role.ADMIN);
            User user = new User(userDTO);
            userRepository.save(user);
        });
    }
}
