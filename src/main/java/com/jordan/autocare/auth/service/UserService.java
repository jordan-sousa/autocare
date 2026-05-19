package com.jordan.autocare.auth.service;

import com.jordan.autocare.auth.domain.User;
import com.jordan.autocare.auth.dto.UserCreateRequest;
import com.jordan.autocare.auth.dto.UserResponse;
import com.jordan.autocare.auth.exception.UserNotFoundExcepition;
import com.jordan.autocare.auth.mapper.UserMapper;
import com.jordan.autocare.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse create(UserCreateRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("O e-mail já existe");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User saved = userRepository.save(user);
        return UserMapper.ToResponse(saved);
    }

    @Transactional(readOnly = true)
    public User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundExcepition(id));
    }
}
