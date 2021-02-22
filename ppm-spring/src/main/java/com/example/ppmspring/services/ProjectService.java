package com.example.ppmspring.services;

import com.example.ppmspring.domain.Project;

public interface ProjectService {

    Project saveOrUpdateProject(Project project);
    Project findProjectByIdentifier(String projectIdentifier);
}
