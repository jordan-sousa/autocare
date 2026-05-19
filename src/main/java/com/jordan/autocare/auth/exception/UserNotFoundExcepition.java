package com.jordan.autocare.auth.exception;

public class UserNotFoundExcepition extends RuntimeException{

    public UserNotFoundExcepition(Long id) {
        super("Usuario não encontrado com id: " + id);
    }
}
