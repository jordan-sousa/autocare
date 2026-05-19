package com.jordan.autocare.auth.dto;

public record UserResponse(
        Long id,
        String name,
        String email
) {
}
