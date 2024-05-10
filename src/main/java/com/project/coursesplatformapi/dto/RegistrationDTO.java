package com.project.coursesplatformapi.dto;

import com.project.coursesplatformapi.model.Course;
import com.project.coursesplatformapi.model.User;

public record RegistrationDTO(User user, Course course) {
}
