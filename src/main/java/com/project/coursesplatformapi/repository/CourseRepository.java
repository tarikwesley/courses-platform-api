package com.project.coursesplatformapi.repository;

import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);

    Page<Course> findAll(Status status, Pageable page);
}
