package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.RegistrationDTO;
import com.project.coursesplatformapi.exception.CourseException;
import com.project.coursesplatformapi.exception.RegistrationException;
import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.model.enums.Status;
import com.project.coursesplatformapi.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final UserService userService;
    private final CourseService courseService;

    public RegistrationService(RegistrationRepository registrationRepository, UserService userService, CourseService courseService) {
        this.registrationRepository = registrationRepository;
        this.userService = userService;
        this.courseService = courseService;
    }

    public Registration registerUserToCourse(RegistrationDTO registrationDTO) {
        if (registrationRepository.existsByUserIdAndCourseId(registrationDTO.userId(), registrationDTO.courseId()))
            throw new RegistrationException("User is already registered to this course.");

        User user = userService.findUserById(registrationDTO.userId());

        Course course = courseService.findCourseById(registrationDTO.courseId());
        if (course.getStatus().name().equals(Status.INACTIVE.name()))
            throw new CourseException("Course is inactive. You can't register to inactive course.");

        Registration registration = new Registration(user, course);
        registrationRepository.save(registration);
        return registration;
    }

    public Registration getRegistration(Long userId, Long courseId) {
        return registrationRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new RegistrationException("Registration not found."));
    }

    public List<Registration> getRegistrations() {
        return registrationRepository.findAll();
    }
}