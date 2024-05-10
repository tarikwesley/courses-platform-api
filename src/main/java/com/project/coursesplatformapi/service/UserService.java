package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.UserDTO;
import com.project.coursesplatformapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
