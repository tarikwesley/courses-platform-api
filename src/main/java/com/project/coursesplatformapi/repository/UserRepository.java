package com.project.coursesplatformapi.repository;

import com.project.coursesplatformapi.dto.UserDTO;
import com.project.coursesplatformapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDTO findByUserName(String userName);
}
