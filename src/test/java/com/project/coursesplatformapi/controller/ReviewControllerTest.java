package com.project.coursesplatformapi.controller;

import com.project.coursesplatformapi.dto.NpsDTO;
import com.project.coursesplatformapi.dto.ReviewDTO;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.model.Review;
import com.project.coursesplatformapi.service.NpsService;
import com.project.coursesplatformapi.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private NpsService npsService;

    @InjectMocks
    private ReviewController reviewController;

    @Test
    void shouldAddReview() {
        ReviewDTO reviewDTO = new ReviewDTO(1L, 1L, 8, "Good");
        Review review = new Review(reviewDTO, new Registration());

        when(reviewService.addReview(reviewDTO)).thenReturn(review);

        ResponseEntity<Review> responseEntity = reviewController.addReview(reviewDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(review, responseEntity.getBody());
    }

    @Test
    void shouldReturnReviewsByUser() {
        Long userId = 1L;
        List<Review> reviews = new ArrayList<>();
        when(reviewService.getReviewsByUser(userId)).thenReturn(reviews);

        ResponseEntity<List<Review>> responseEntity = reviewController.getReviewsByUser(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(reviews, responseEntity.getBody());
    }

    @Test
    void shouldReturnReviewsByCourse() {
        Long courseId = 1L;
        List<Review> reviews = new ArrayList<>();
        when(reviewService.getReviewsByCourse(courseId)).thenReturn(reviews);

        ResponseEntity<List<Review>> responseEntity = reviewController.getReviewsByCourse(courseId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(reviews, responseEntity.getBody());
    }

    @Test
    void shouldReturnNps() {
        Map<String, NpsDTO> npsMap = new HashMap<>();
        when(npsService.calculateNpsForCoursesWithFourOrMoreRegistrations()).thenReturn(npsMap);

        ResponseEntity<Map<String, NpsDTO>> responseEntity = reviewController.getNps();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(npsMap, responseEntity.getBody());
    }
}
