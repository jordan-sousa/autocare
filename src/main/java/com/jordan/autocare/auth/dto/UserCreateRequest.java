package com.jordan.autocare.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank(message = "Nome é obrigatorio")
        String name,

        @Email(message = "Email invalido")
        @NotBlank(message = "Email é ogrigatorio")
        String email,

        @NotBlank(message = "Senha é obrigatorio")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
        String password
) {
}
