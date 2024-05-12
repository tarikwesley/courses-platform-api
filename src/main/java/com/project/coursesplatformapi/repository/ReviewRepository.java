package com.project.coursesplatformapi.repository;

import com.project.coursesplatformapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    List<Review> findByCourseId(Long courseId);

    List<Review> findByUserId(Long userId);
}
