package com.project.coursesplatformapi.repository;

import com.project.coursesplatformapi.dto.UserResponseDTO;
import com.project.coursesplatformapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    UserResponseDTO findByUsername(String username);
}
