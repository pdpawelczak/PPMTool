package com.example.ppmspring.controllers;

import com.example.ppmspring.domain.User;
import com.example.ppmspring.services.MapValidationErrorService;
import com.example.ppmspring.services.UserService;
import com.example.ppmspring.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final MapValidationErrorService mapValidationErrorService;
    private final UserService userService;
    private final UserValidator userValidator;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        userValidator.validate(user, result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }
        User newUser = userService.saveUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }
}
