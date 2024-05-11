package com.project.coursesplatformapi.repository;

import com.project.coursesplatformapi.dto.UserResponseDTO;
import com.project.coursesplatformapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserResponseDTO> findByUsername(String username);

    Optional<UserResponseDTO> findByUsernameOrEmail(String username, String email);
}
