package com.project.coursesplatformapi.controller;

import com.project.coursesplatformapi.dto.RegistrationDTO;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<Registration> createRegistration(@Valid @RequestBody RegistrationDTO registrationDTO) {
        return ResponseEntity.ok(registrationService.registerUserToCourse(registrationDTO));

    }
}
