package com.jordan.autocare.auth.mapper;

import com.jordan.autocare.auth.domain.User;
import com.jordan.autocare.auth.dto.UserResponse;

public class UserMapper {

    public static UserResponse ToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
