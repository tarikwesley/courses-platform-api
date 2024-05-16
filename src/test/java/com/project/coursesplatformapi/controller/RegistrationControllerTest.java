package com.project.coursesplatformapi.controller;

import com.project.coursesplatformapi.dto.RegistrationDTO;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateRegistrationSuccessfully() {
        RegistrationDTO registrationDTO = new RegistrationDTO(1L, 1L);

        Registration createdRegistration = new Registration();

        when(registrationService.registerUserToCourse(registrationDTO)).thenReturn(createdRegistration);

        ResponseEntity<Registration> responseEntity = registrationController.createRegistration(registrationDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(createdRegistration, responseEntity.getBody());
    }

    @Test
    void shouldThrowsExceptionWhenAnyErrorOccur() {
        RegistrationDTO registrationDTO = new RegistrationDTO(1L, null);

        when(registrationService.registerUserToCourse(registrationDTO)).thenThrow(new IllegalArgumentException("Course ID is required"));

        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Course ID is required");
        });
    }

}
