package com.project.coursesplatformapi.dto;

import com.project.coursesplatformapi.model.enums.Role;

public record UserResponseDTO(String name, String email, Role role) {
}
