package com.project.coursesplatformapi.dto;

import jakarta.validation.constraints.NotBlank;

public record CourseDTO(@NotBlank String name, @NotBlank String code, @NotBlank String instructor, @NotBlank String description) {
}
