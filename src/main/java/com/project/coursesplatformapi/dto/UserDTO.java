package com.project.coursesplatformapi.dto;

import com.project.coursesplatformapi.model.enums.Role;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank String name, @NotBlank String username, @NotBlank String email,
                      @NotBlank String password, @NotBlank Role role) {
}
