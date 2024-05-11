package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.RegistrationDTO;
import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.model.enums.Status;
import com.project.coursesplatformapi.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

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
        if (registrationRepository.existsByUserIdAndCourseId(registrationDTO.user_id(), registrationDTO.course_id()))
            throw new RuntimeException("User is already registered to this course.");

        User user = userService.findUserById(registrationDTO.user_id());
        if (user.getRole().name().equals(Status.INACTIVE.name()))
            throw new RuntimeException("User is inactive. You can't register inactive user to course.");

        Course course = courseService.findCourseById(registrationDTO.course_id());
        if (course.getStatus().name().equals(Status.INACTIVE.name()))
            throw new RuntimeException("Course is inactive. You can't register to inactive course.");

        Registration registration = new Registration(user, course);
        registrationRepository.save(registration);
        return registration;
    }
}