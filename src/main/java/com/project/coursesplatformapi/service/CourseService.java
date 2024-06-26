package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.CourseDTO;
import com.project.coursesplatformapi.exception.CourseException;
import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.enums.Role;
import com.project.coursesplatformapi.model.enums.Status;
import com.project.coursesplatformapi.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;

    public CourseService(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public Course disableCourse(String code) {
        Course course = courseRepository.findByCode(code).orElseThrow(() -> new CourseException("Course not found"));
        course.setStatus(Status.INACTIVE);
        course.setInactivatedAt(LocalDate.now());
        courseRepository.save(course);
        return course;
    }

    public Page<Course> getAllCourses(Status status, Pageable page) {
        if (status == null) return courseRepository.findAll(page);
        return courseRepository.findByStatus(status, page);
    }

    public Course createCourse(CourseDTO courseDTO) {
        var instructor = userService.getUserByUsername(courseDTO.instructor());
        if (!instructor.getRole().equals(Role.INSTRUCTOR))
            throw new CourseException("User is not an instructor and can't create a course.");

        courseRepository.findByCode(courseDTO.code()).ifPresent(course -> {
            throw new CourseException("Course with this code already exists.");
        });

        Course course = new Course(courseDTO);
        courseRepository.save(course);
        return course;
    }

    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseException("Course not found"));
    }
}
