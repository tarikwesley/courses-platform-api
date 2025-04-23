package com.project.coursesplatformapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.coursesplatformapi.dto.ReviewDTO;
import com.project.coursesplatformapi.exception.ReviewException;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.model.Review;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RegistrationService registrationService;
    private final NotificationService notificationService;
    private final UserService userService;

    public ReviewService(ReviewRepository reviewRepository, RegistrationService registrationService, NotificationService notificationService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.registrationService = registrationService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    public Review addReview(ReviewDTO reviewDTO) {
        if (reviewRepository.existsByUserIdAndCourseId(reviewDTO.userId(), reviewDTO.courseId()))
            throw new ReviewException("This user has already reviewed this course");

        Registration registration = registrationService.getRegistration(reviewDTO.userId(), reviewDTO.courseId());
        Review review = new Review(reviewDTO, registration);
        reviewRepository.save(review);

        if (review.getRating() < 6) {
            User user = userService.getUserByUsername(registration.getCourse().getInstructor());
            try {
                notificationService.emailSender(user.getEmail(), "New review added", review);
            } catch (JsonProcessingException e) {
                throw new ReviewException("Error while processing review for email notification");
            }
        }
        return review;
    }

    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Review> getReviewsByCourse(Long courseId) {
        return reviewRepository.findByCourseId(courseId);
    }
}
