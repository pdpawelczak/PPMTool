package com.example.ppmspring.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProjectNotFoundExceptionResponse {

    private final String ProjectNotFound;
}
