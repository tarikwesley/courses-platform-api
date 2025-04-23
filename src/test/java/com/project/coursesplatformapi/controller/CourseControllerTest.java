package com.project.coursesplatformapi.controller;

import com.project.coursesplatformapi.dto.CourseDTO;
import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.enums.Status;
import com.project.coursesplatformapi.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    @WithMockUser(authorities = {"SCOPE_ADMIN"})
    void shouldCreateCourse() {
        CourseDTO courseDTO = new CourseDTO("Java", "java", "lucas", "Introduction programming java");
        Course course = new Course(courseDTO);

        when(courseService.createCourse(courseDTO)).thenReturn(course);

        ResponseEntity<Course> response = courseController.createCourse(courseDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(course, response.getBody());
        verify(courseService, times(1)).createCourse(courseDTO);
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_ADMIN"})
    void shouldDisableCourse() {
        String code = "CODE";
        Course course = new Course();
        course.setCode(code);

        when(courseService.disableCourse(code)).thenReturn(course);

        ResponseEntity<Course> response = courseController.disableCourse(code);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
        verify(courseService, times(1)).disableCourse(code);
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_ADMIN"})
    void shouldReturnAllCourses() {
        Status status = Status.ACTIVE;
        Pageable page = Pageable.unpaged();
        Page<Course> courses = new PageImpl<>(Collections.emptyList());

        when(courseService.getAllCourses(status, page)).thenReturn(courses);

        ResponseEntity<Page<Course>> response = courseController.getAllCourses(status, page);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courses, response.getBody());
        verify(courseService, times(1)).getAllCourses(status, page);
    }
}
