package com.project.coursesplatformapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coursesplatformapi.dto.ReviewDTO;
import com.project.coursesplatformapi.exception.ReviewException;
import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.model.Review;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.model.enums.Role;
import com.project.coursesplatformapi.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private UserService userService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldThrowsExceptionWhenUserAlreadyEvaluatedCourse() {
        ReviewDTO reviewDTO = new ReviewDTO(1L, 1L, 8, "Good course");

        when(reviewRepository.existsByUserIdAndCourseId(reviewDTO.userId(), reviewDTO.courseId())).thenReturn(true);

        assertThrows(ReviewException.class, () -> reviewService.addReview(reviewDTO));
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void shouldSendsEmailNotificationWhenAddRatingLessThan6() throws JsonProcessingException {
        User user = new User();
        user.setRole(Role.INSTRUCTOR);
        user.setUsername("thales");
        user.setEmail("thales@email.com");
        Course course = new Course();
        course.setInstructor("thales");
        course.setName("New Relic");
        ReviewDTO reviewDTO = new ReviewDTO(1L, 1L, 4, "Bad course");
        Registration registration = new Registration();
        registration.setCourse(course);
        when(reviewRepository.existsByUserIdAndCourseId(reviewDTO.userId(), reviewDTO.courseId())).thenReturn(false);
        when(registrationService.getRegistration(reviewDTO.userId(), reviewDTO.courseId())).thenReturn(registration);
        when(userService.getUserByUsername(registration.getCourse().getInstructor())).thenReturn(user);
        doNothing().when(notificationService).emailSender(eq(user.getEmail()), anyString(), any());

        Review addedReview = reviewService.addReview(reviewDTO);

        assertNotNull(addedReview);
        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(notificationService, times(1)).emailSender(eq(user.getEmail()), anyString(), any());
    }

    @Test
    void shouldNoEmailNotificationWhenRatingNoLessThan6() throws JsonProcessingException {
        User user = new User();
        user.setRole(Role.INSTRUCTOR);
        user.setUsername("alana");
        Course course = new Course();
        course.setName("DataDog");
        course.setInstructor("alana");
        ReviewDTO reviewDTO = new ReviewDTO(1L, 1L, 10, "Excellent course");
        Registration registration = new Registration();
        registration.setCourse(course);
        when(reviewRepository.existsByUserIdAndCourseId(reviewDTO.userId(), reviewDTO.courseId())).thenReturn(false);
        when(registrationService.getRegistration(reviewDTO.userId(), reviewDTO.courseId())).thenReturn(registration);
        when(userService.getUserByUsername(registration.getCourse().getInstructor())).thenReturn(user);
        doNothing().when(notificationService).emailSender(anyString(), anyString(), any());

        Review addedReview = reviewService.addReview(reviewDTO);

        assertNotNull(addedReview);
        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(notificationService, never()).emailSender(anyString(), anyString(), any());
    }

    @Test
    void shouldThrowExceptionWhenNotificationFails() throws JsonProcessingException {
        User user = new User();
        user.setRole(Role.INSTRUCTOR);
        user.setUsername("alana");
        user.setEmail("alana@email.com");
        Course course = new Course();
        course.setName("DataDog");
        course.setInstructor("alana");
        ReviewDTO reviewDTO = new ReviewDTO(1L, 1L, 4, "Bad course");
        Registration registration = new Registration();
        registration.setUser(user);
        registration.setCourse(course);
        when(reviewRepository.existsByUserIdAndCourseId(reviewDTO.userId(), reviewDTO.courseId())).thenReturn(false);
        when(registrationService.getRegistration(reviewDTO.userId(), reviewDTO.courseId())).thenReturn(registration);
        when(userService.getUserByUsername(registration.getCourse().getInstructor())).thenReturn(user);
        doThrow(JsonProcessingException.class).when(objectMapper).writeValueAsString(any());
        doThrow(JsonProcessingException.class).when(notificationService).emailSender(eq(user.getEmail()), anyString(), any());

        assertThrows(ReviewException.class, () -> reviewService.addReview(reviewDTO));
    }

    @Test
    void shouldReturnAllReviewsByUser() {
        Long userId = 1L;
        List<Review> expectedReviews = Collections.singletonList(new Review());
        when(reviewRepository.findByUserId(userId)).thenReturn(expectedReviews);

        List<Review> retrievedReviews = reviewService.getReviewsByUser(userId);

        assertNotNull(retrievedReviews);
        assertEquals(expectedReviews, retrievedReviews);
    }

    @Test
    void shouldReturnAllReviewsByCourse() {
        Long courseId = 1L;
        List<Review> expectedReviews = Collections.singletonList(new Review());
        when(reviewRepository.findByCourseId(courseId)).thenReturn(expectedReviews);

        List<Review> retrievedReviews = reviewService.getReviewsByCourse(courseId);

        assertNotNull(retrievedReviews);
        assertEquals(expectedReviews, retrievedReviews);
    }
}
