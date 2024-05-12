package com.project.coursesplatformapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewDTO(@NotNull Long userId, @NotNull Long courseId,
                        @NotNull @Min(value = 0, message = "Rating must be at least 0")
                        @Max(value = 10, message = "Rating must be at most 10") Integer rating,
                        @NotBlank String comment) {
}
