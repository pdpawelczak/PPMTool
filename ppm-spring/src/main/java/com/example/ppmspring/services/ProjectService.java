package com.example.ppmspring.services;

import com.example.ppmspring.domain.Project;

public interface ProjectService {

    Project saveOrUpdateProject(Project project, String username);
    Project findByProjectIdentifier(String projectIdentifier);
    Iterable<Project> findAllProjects();
    void deleteProjectByIdentifier(String projectIdentifier);
}
