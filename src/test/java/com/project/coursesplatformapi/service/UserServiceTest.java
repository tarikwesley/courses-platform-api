package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.UserDTO;
import com.project.coursesplatformapi.exception.UserException;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.model.enums.Role;
import com.project.coursesplatformapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_ADMIN"})
    void shouldReturnUserByUsernameWhenUserExist() {
        String username = "lucas";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User returnedUser = userService.getUserByUsername(username);

        assertNotNull(returnedUser);
        assertEquals(username, returnedUser.getUsername());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_ADMIN"})
    void shouldThrowsExceptionWhenInSearchUserByUsernameNotExist() {
        String username = "nonExistingUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    void shouldReturnNewUserCreated() {
        UserDTO userDTO = new UserDTO("Nath", "nath", "nath@email.com", "password", Role.STUDENT);

        when(userRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());

        User createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(userDTO.username(), createdUser.getUsername());
        assertTrue(passwordEncoder.matches(userDTO.password(), createdUser.getPassword()));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowAnExceptionWhenTryingToCreateAUserThatAlreadyExists() {
        UserDTO existingUserDTO = new UserDTO("Lucas", "lucas", "lucas@email.com", "password", Role.INSTRUCTOR);

        when(userRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(new User()));

        assertThrows(UserException.class, () -> userService.createUser(existingUserDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldFindUserByIdIfExistReturnUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User returnedUser = userService.findUserById(userId);

        assertNotNull(returnedUser);
        assertEquals(userId, returnedUser.getId());
    }

    @Test
    void shouldThrowAnExceptionWhenNotFindUserById() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> userService.findUserById(userId));
    }
}
