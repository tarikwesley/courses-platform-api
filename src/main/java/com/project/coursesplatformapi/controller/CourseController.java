package com.project.coursesplatformapi.controller;

import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.enums.Status;
import com.project.coursesplatformapi.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Course> disableCourse(@PathVariable(name = "code") String code) {
        return ResponseEntity.ok(courseService.disableCourse(code));
    }

    @GetMapping
    public ResponseEntity<Page<Course>> getAllCourses(@Valid @RequestParam(required = false) Status status, @PageableDefault(size = 10, page = 0, sort = "id") Pageable page) {
        return ResponseEntity.ok(courseService.getAllCourses(status, page));
    }
}
