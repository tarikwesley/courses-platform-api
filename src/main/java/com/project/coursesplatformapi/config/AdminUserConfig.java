package com.project.coursesplatformapi.config;

import com.project.coursesplatformapi.dto.UserDTO;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.model.enums.Role;
import com.project.coursesplatformapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Value("${user.username}")
    private String username;

    @Value("${user.password}")
    private String password;

    @Value("${user.email}")
    private String email;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (username == null || password == null || email == null) {
            log.warn("Admin user config skipped: missing environment variables.");
            return;
        }

        userRepository.findByUsername(username).ifPresentOrElse(
                user -> log.info("Admin user '{}' already exists", username),
                () -> {
                    var userDTO = new UserDTO(username, username, email, passwordEncoder.encode(password), Role.ADMIN);
                    User user = new User(userDTO);
                    userRepository.save(user);
                    log.info("Admin user '{}' created successfully", username);
                }
        );
    }
}
