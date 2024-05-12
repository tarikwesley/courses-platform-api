package com.project.coursesplatformapi.controller;

import com.project.coursesplatformapi.dto.NpsDTO;
import com.project.coursesplatformapi.dto.ReviewDTO;
import com.project.coursesplatformapi.model.Review;
import com.project.coursesplatformapi.service.NpsService;
import com.project.coursesplatformapi.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final NpsService npsService;

    public ReviewController(ReviewService reviewService, NpsService npsService) {
        this.reviewService = reviewService;
        this.npsService = npsService;
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return new ResponseEntity<>(reviewService.addReview(reviewDTO), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Review>> getReviewsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(reviewService.getReviewsByCourse(courseId));
    }

    @GetMapping("/nps")
    public ResponseEntity<Map<String, NpsDTO>> getNps() {
        return ResponseEntity.ok(npsService.calculateNpsForCoursesWithFourOrMoreRegistrations());
    }
}
