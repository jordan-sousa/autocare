package com.jordan.autocare.auth.controller;

import com.jordan.autocare.auth.dto.UserCreateRequest;
import com.jordan.autocare.auth.dto.UserResponse;
import com.jordan.autocare.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody @Valid UserCreateRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.create(request));
    }
}
