package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.UserDTO;
import com.project.coursesplatformapi.exception.UserException;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserException("User not found"));
    }

    public User createUser(UserDTO userDTO) {
        userRepository.findByUsernameOrEmail(userDTO.username(), userDTO.email()).ifPresent(user -> {
            throw new UserException("User already exists");
        });
        User user = new User(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        userRepository.save(user);
        return user;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
    }
}
