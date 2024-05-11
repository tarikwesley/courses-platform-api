package com.project.coursesplatformapi.dto;

import jakarta.validation.constraints.NotNull;

public record RegistrationDTO(@NotNull Long user_id, @NotNull Long course_id) {
}
