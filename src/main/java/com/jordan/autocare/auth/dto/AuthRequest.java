package com.jordan.autocare.auth.dto;

public record AuthRequest(
        String email,
        String password
) {
}
