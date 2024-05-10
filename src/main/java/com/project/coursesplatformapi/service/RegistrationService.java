package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.RegistrationDTO;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public Registration registerUserToCourse(RegistrationDTO registrationDTO) {
        if (registrationDTO.course().getStatus().name().equals("INACTIVE"))
            throw new RuntimeException("Course is inactive. You can't register to inactive course.");

        Registration registration = new Registration(registrationDTO);
        registrationRepository.save(registration);
        return registration;
    }
}