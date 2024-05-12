package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.UserDTO;
import com.project.coursesplatformapi.dto.UserResponseDTO;
import com.project.coursesplatformapi.exception.UserException;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserException("User not found"));
    }

    public User createUser(UserDTO userDTO) {
        userRepository.findByUsernameOrEmail(userDTO.username(), userDTO.email()).ifPresent(user -> {
            throw new UserException("User already exists");
        });
        User user = new User(userDTO);
        userRepository.save(user);
        return user;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
    }
}
