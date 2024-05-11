package com.project.coursesplatformapi.dto;

import com.project.coursesplatformapi.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(@NotBlank String name,
                      @NotBlank @Pattern(regexp = "[a-z]*") @Size(max = 20) String username,
                      @NotBlank @Email String email,
                      @NotBlank String password,
                      @NotNull Role role) {
}
