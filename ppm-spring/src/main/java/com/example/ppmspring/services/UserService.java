package com.example.ppmspring.services;

import com.example.ppmspring.domain.User;
import com.example.ppmspring.payload.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User saveUser(User user);
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
}
