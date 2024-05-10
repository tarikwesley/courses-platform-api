package com.project.coursesplatformapi.controller;

import com.project.coursesplatformapi.dto.UserDTO;
import com.project.coursesplatformapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "userName") String userName) {
        return ResponseEntity.ok().body(userService.getUserByUserName(userName));
    }
}
