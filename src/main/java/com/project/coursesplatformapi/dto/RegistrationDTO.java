package com.project.coursesplatformapi.dto;

import jakarta.validation.constraints.NotNull;

public record RegistrationDTO(@NotNull Long userId, @NotNull Long courseId) {
}
