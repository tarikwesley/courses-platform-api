package com.project.coursesplatformapi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CourseDTO(@NotBlank String name,
                        @NotBlank @Size(max = 10) @Pattern(regexp = "^[a-zA-Z]+(-[a-zA-Z]+){0,4}$\n") String code,
                        @NotBlank String instructor,
                        @NotBlank String description) {
}
