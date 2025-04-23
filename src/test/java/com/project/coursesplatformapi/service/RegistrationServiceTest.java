package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.RegistrationDTO;
import com.project.coursesplatformapi.exception.CourseException;
import com.project.coursesplatformapi.exception.RegistrationException;
import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.model.enums.Status;
import com.project.coursesplatformapi.repository.RegistrationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private UserService userService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void shouldThrowsExceptionWhenTryingRegisterUserToCourseIfUserAlreadyRegistered() {
        RegistrationDTO registrationDTO = new RegistrationDTO(1L, 1L);
        when(registrationRepository.existsByUserIdAndCourseId(registrationDTO.userId(), registrationDTO.courseId())).thenReturn(true);

        assertThrows(RegistrationException.class, () -> registrationService.registerUserToCourse(registrationDTO));
        verify(registrationRepository, never()).save(any(Registration.class));
    }

    @Test
    void shouldThrowsExceptionWhenTryingRegisterUserToCourseOfCourseInactive() {
        Course course = new Course();
        course.setStatus(Status.INACTIVE);
        RegistrationDTO registrationDTO = new RegistrationDTO(1L, 1L);
        when(registrationRepository.existsByUserIdAndCourseId(registrationDTO.userId(), registrationDTO.courseId())).thenReturn(false);
        when(courseService.findCourseById(registrationDTO.courseId())).thenReturn(course);

        assertThrows(CourseException.class, () -> registrationService.registerUserToCourse(registrationDTO));
        verify(registrationRepository, never()).save(any(Registration.class));
    }

    @Test
    void shouldRegisterUserToCourseSuccessfully() {
        Course course = new Course();
        course.setStatus(Status.ACTIVE);
        RegistrationDTO registrationDTO = new RegistrationDTO(1L, 1L);
        when(registrationRepository.existsByUserIdAndCourseId(registrationDTO.userId(), registrationDTO.courseId())).thenReturn(false);
        when(courseService.findCourseById(registrationDTO.courseId())).thenReturn(course);
        when(userService.findUserById(registrationDTO.userId())).thenReturn(new User());

        Registration registration = registrationService.registerUserToCourse(registrationDTO);

        assertNotNull(registration);
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }

    @Test
    void shouldReturnRegisterIfExist() {
        Long userId = 1L;
        Long courseId = 1L;
        Registration expectedRegistration = new Registration();
        when(registrationRepository.findByUserIdAndCourseId(userId, courseId)).thenReturn(Optional.of(expectedRegistration));

        Registration retrievedRegistration = registrationService.getRegistration(userId, courseId);

        assertNotNull(retrievedRegistration);
        assertEquals(expectedRegistration, retrievedRegistration);
    }

    @Test
    void shouldThrowsExceptionWhenRegistrationIfNotExist() {
        Long userId = 1L;
        Long courseId = 1L;
        when(registrationRepository.findByUserIdAndCourseId(userId, courseId)).thenReturn(Optional.empty());

        assertThrows(RegistrationException.class, () -> registrationService.getRegistration(userId, courseId));
    }

    @Test
    void shouldReturnAllRegistrations() {
        List<Registration> expectedRegistrations = Collections.singletonList(new Registration());
        when(registrationRepository.findAll()).thenReturn(expectedRegistrations);

        List<Registration> retrievedRegistrations = registrationService.getRegistrations();

        assertNotNull(retrievedRegistrations);
        assertEquals(expectedRegistrations, retrievedRegistrations);
    }
}
