package com.project.coursesplatformapi.controller;

import com.project.coursesplatformapi.dto.UserDTO;
import com.project.coursesplatformapi.dto.UserResponseDTO;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.model.enums.Role;
import com.project.coursesplatformapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUser() {
        UserDTO userDTO = new UserDTO("John", "john", "john@email.com", "password", Role.ADMIN);
        User user = new User(userDTO);


        when(userService.createUser(userDTO)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).createUser(userDTO);
    }

    @Test
    void shouldReturnUser() {
        String username = "john";
        User user = new User();
        user.setName("John");
        user.setEmail("john@email.com");
        user.setRole(Role.ADMIN);

        when(userService.getUserByUsername(username)).thenReturn(user);

        ResponseEntity<UserResponseDTO> response = userController.getUser(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getName(), response.getBody().name());
        assertEquals(user.getEmail(), response.getBody().email());
        assertEquals(user.getRole(), response.getBody().role());
        verify(userService, times(1)).getUserByUsername(username);
    }
}
