package com.project.coursesplatformapi.service;

import com.project.coursesplatformapi.dto.CourseDTO;
import com.project.coursesplatformapi.exception.CourseException;
import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.User;
import com.project.coursesplatformapi.model.enums.Role;
import com.project.coursesplatformapi.model.enums.Status;
import com.project.coursesplatformapi.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldDisableCourseIfExist() {
        String code = "ABC123";
        Course course = new Course();

        when(courseRepository.findByCode(code)).thenReturn(Optional.of(course));

        Course disabledCourse = courseService.disableCourse(code);

        assertNotNull(disabledCourse);
        assertEquals(Status.INACTIVE, disabledCourse.getStatus());
        assertNotNull(disabledCourse.getInactivatedAt());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void shouldThrowAnExceptionWhenTryingToDisableTheCourseThatDoesNotExist() {
        String code = "NonExistingCourse";

        when(courseRepository.findByCode(code)).thenReturn(Optional.empty());

        assertThrows(CourseException.class, () -> courseService.disableCourse(code));
    }

    @Test
    void shouldReturnAllCoursesInPaginating() {
        Page<Course> expectedPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(courseRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Course> resultPage = courseService.getAllCourses(null, pageable);

        assertEquals(expectedPage, resultPage);
        verify(courseRepository, times(1)).findAll(pageable);
        verify(courseRepository, never()).findByStatus(any(), any());
    }

    @Test
    void shouldReturnAllCoursesInPaginationFilteringByStatus() {
        Status status = Status.ACTIVE;
        Page<Course> expectedPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(courseRepository.findByStatus(status, pageable)).thenReturn(expectedPage);

        Page<Course> resultPage = courseService.getAllCourses(status, pageable);

        assertEquals(expectedPage, resultPage);
        verify(courseRepository, times(1)).findByStatus(status, pageable);
        verify(courseRepository, never()).findAll((Pageable) any());
    }

    @Test
    void shouldCreatingCourseIfUserIsTypeInstructor() {
        CourseDTO courseDTO = new CourseDTO("Java", "java", "lucas", "Introduction programming java");
        User user = new User();
        user.setRole(Role.INSTRUCTOR);

        when(userService.getUserByUsername(courseDTO.instructor())).thenReturn(user);

        Course createdCourse = courseService.createCourse(courseDTO);

        assertNotNull(createdCourse);
        assertEquals(courseDTO.code(), createdCourse.getCode());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldThrowsExceptionWhenTryingCreatingCourseIfUserIsNotTypeInstructor() {
        User user = new User();
        user.setRole(Role.STUDENT);
        CourseDTO courseDTO = new CourseDTO("AWS", "aws", "nonInstructor", "Introduction AWS");

        when(userService.getUserByUsername(courseDTO.instructor())).thenReturn(user);

        when(userService.getUserByUsername(courseDTO.instructor())).thenReturn(user);

        assertThrows(CourseException.class, () -> courseService.createCourse(courseDTO));
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void shouldThrowsExceptionWhenTryingCreatingCourseIfCodeAlreadyExist() {
        User user = new User();
        user.setRole(Role.INSTRUCTOR);
        CourseDTO courseDTO = new CourseDTO("Spring Boot", "spring-boot", "lucas", "Introduction programming java with spring boot");

        when(userService.getUserByUsername(courseDTO.instructor())).thenReturn(user);
        when(courseRepository.findByCode(courseDTO.code())).thenReturn(Optional.of(new Course()));

        assertThrows(CourseException.class, () -> courseService.createCourse(courseDTO));
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void shouldFindCourseByIdIfExistReturnCourse() {
        Long id = 1L;
        Course course = new Course();
        course.setId(id);

        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        Course returnedCourse = courseService.findCourseById(id);

        assertNotNull(returnedCourse);
        assertEquals(id, returnedCourse.getId());
    }

    @Test
    void shouldThrowAnExceptionWhenNotFindCourseById() {
        Long id = 1L;

        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CourseException.class, () -> courseService.findCourseById(id));
    }
}
