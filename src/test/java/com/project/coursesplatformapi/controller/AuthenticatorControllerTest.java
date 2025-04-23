package com.project.coursesplatformapi.controller;

import com.project.coursesplatformapi.dto.LoginRequestDTO;
import com.project.coursesplatformapi.dto.LoginResponseDTO;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.model.enums.Role;
import com.project.coursesplatformapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticatorControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticatorController authenticatorController;

    @Test
    void shouldLoginSuccess() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("wesley", "password");

        User user = new User();
        user.setUsername("wesley");
        user.setPassword(passwordEncoder.encode(loginRequestDTO.password()));
        user.setRole(Role.ADMIN);

        when(userService.getUserByUsername(loginRequestDTO.username())).thenReturn(user);
        when(passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())).thenReturn(true);

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("tokenValue");

        when(jwtEncoder.encode(any())).thenReturn(jwt);

        ResponseEntity<LoginResponseDTO> responseEntity = authenticatorController.login(loginRequestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("tokenValue", responseEntity.getBody().token());
        assertEquals(300L, responseEntity.getBody().expiresIn());
    }

    @Test
    void shouldErrorInLoginInvalidCredentials() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("username", "password");

        when(userService.getUserByUsername("username")).thenReturn(null);

        assertThrows(BadCredentialsException.class, () -> authenticatorController.login(loginRequestDTO));
    }
}
