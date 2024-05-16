package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.NpsDTO;
import com.project.coursesplatformapi.dto.ReviewDTO;
import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.model.Review;
import com.project.coursesplatformapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class NpsServiceTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private NpsService npsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCalculateNpsForCoursesWithPositiveNps() {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("JUnit");
        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("Mockito");

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId((long) (i + 1));
            users.add(user);
        }

        List<Registration> registrations = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            registrations.add(new Registration(users.get(i), course1));
        }

        List<ReviewDTO> reviewsDTO = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            reviewsDTO.add(new ReviewDTO((long) (i + 1), course1.getId(), 10, "Excellent"));
        }

        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            reviews.add(new Review(reviewsDTO.get(i), registrations.get(i)));
        }

        when(registrationService.getRegistrations()).thenReturn(registrations);
        when(reviewService.getReviewsByCourse(course1.getId())).thenReturn(reviews.subList(0, 1));
        when(reviewService.getReviewsByCourse(course2.getId())).thenReturn(new ArrayList<>());

        Map<String, NpsDTO> expected = new HashMap<>();
        expected.put(course1.getName(), new NpsDTO(100, 1, 0, 0));

        Map<String, NpsDTO> result = npsService.calculateNpsForCoursesWithFourOrMoreRegistrations();

        assertEquals(expected, result);
    }

    @Test
    void shouldCalculateNpsForCoursesWithNegativeNps() {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("JUnit");

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            User user = new User();
            user.setId((long) (i + 1));
            users.add(user);
        }

        List<Registration> registrations = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            registrations.add(new Registration(users.get(i), course1));
        }

        List<ReviewDTO> reviewsDTO = new ArrayList<>();
        reviewsDTO.add(new ReviewDTO(1L, course1.getId(), 10, "Excellent"));
        reviewsDTO.add(new ReviewDTO(2L, course1.getId(), 9, "Excellent"));
        reviewsDTO.add(new ReviewDTO(3L, course1.getId(), 8, "Good"));
        reviewsDTO.add(new ReviewDTO(4L, course1.getId(), 5, "Bad"));
        reviewsDTO.add(new ReviewDTO(5L, course1.getId(), 3, "Poor"));
        reviewsDTO.add(new ReviewDTO(6L, course1.getId(), 1, "Awful"));

        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            reviews.add(new Review(reviewsDTO.get(i), registrations.get(i)));
        }

        when(registrationService.getRegistrations()).thenReturn(registrations);
        when(reviewService.getReviewsByCourse(course1.getId())).thenReturn(reviews);

        Map<String, NpsDTO> expected = new HashMap<>();
        expected.put(course1.getName(), new NpsDTO(-16, 2, 3, 1));

        Map<String, NpsDTO> result = npsService.calculateNpsForCoursesWithFourOrMoreRegistrations();

        assertEquals(expected, result);
    }
}
