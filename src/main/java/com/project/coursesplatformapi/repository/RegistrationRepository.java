package com.project.coursesplatformapi.repository;

import com.project.coursesplatformapi.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByUserIdAndCourseId(Long user_id, Long course_id);

    Optional<Registration> findByUserIdAndCourseId(Long userId, Long courseId);
}
