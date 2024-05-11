package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.UserDTO;
import com.project.coursesplatformapi.dto.UserResponseDTO;
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
        return userRepository.findByUsername(username);
    }

    public User createUser(UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.save(user);
        return user;
    }
}
