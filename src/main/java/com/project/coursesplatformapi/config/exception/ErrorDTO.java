package com.project.coursesplatformapi.config.exception;

import org.springframework.http.HttpStatus;

public record ErrorDTO(String message, HttpStatus status) {
}
