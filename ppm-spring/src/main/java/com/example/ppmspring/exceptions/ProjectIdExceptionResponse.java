package com.example.ppmspring.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProjectIdExceptionResponse {

    private final String projectIdentifier;
}
