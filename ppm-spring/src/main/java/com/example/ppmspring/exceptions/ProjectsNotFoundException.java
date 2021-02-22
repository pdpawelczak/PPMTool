package com.example.ppmspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectsNotFoundException extends RuntimeException {

    public ProjectsNotFoundException(String message) {
        super(message);
    }
}
