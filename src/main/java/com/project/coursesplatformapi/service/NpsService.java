package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.NpsDTO;
import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.Registration;
import com.project.coursesplatformapi.model.Review;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NpsService {

    private final ReviewService reviewService;
    private final RegistrationService registrationService;

    public NpsService(ReviewService reviewService, RegistrationService registrationService) {
        this.reviewService = reviewService;
        this.registrationService = registrationService;
    }

    public Map<String, NpsDTO> calculateNpsForCoursesWithFourOrMoreRegistrations() {
        Map<String, NpsDTO> npsMap = new HashMap<>();

        List<Registration> registrations = registrationService.getRegistrations();

        Map<Course, Long> registrationsByCourse = registrations.stream().collect(Collectors.groupingBy(Registration::getCourse, Collectors.counting()));

        List<Course> coursesWithFourOrMoreRegistrations = registrationsByCourse.entrySet().stream()
                .filter(entry -> entry.getValue() > 4)
                .map(Map.Entry::getKey)
                .toList();

        for (Course course : coursesWithFourOrMoreRegistrations) {
            List<Review> reviews = reviewService.getReviewsByCourse(course.getId());
            var nps = getNps(reviews);
            npsMap.put(course.getName(), nps);
        }
        return npsMap;
    }

    private static NpsDTO getNps(List<Review> reviews) {
        int totalReviews = reviews.size();

        int promoters = 0;
        int passives = 0;
        int detractors = 0;
        for (Review review : reviews) {
            if (review.getRating() >= 9 && review.getRating() <= 10) {
                promoters++;
            } else if (review.getRating() >= 6 && review.getRating() <= 8) {
                passives++;
            } else if (review.getRating() >= 0 && review.getRating() < 6) {
                detractors++;
            }
        }
        int nps = (promoters - detractors) * 100 / totalReviews;
        return new NpsDTO(nps, promoters, detractors, passives);
    }
}
