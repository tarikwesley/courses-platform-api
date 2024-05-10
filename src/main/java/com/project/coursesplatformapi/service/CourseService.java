package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.enums.Status;
import com.project.coursesplatformapi.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course disableCourse(String code) {
        Course course = courseRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setStatus(Status.INACTIVE);
        course.setInactivatedAt(LocalDate.now());
        courseRepository.save(course);
        return course;
    }

    public Page<Course> getAllCourses(Status status, Pageable page) {
        return courseRepository.findByStatus(status, page);
    }
}
