package com.example.ppmspring.services;

import com.example.ppmspring.domain.Project;

public interface ProjectService {

    Project saveOrUpdateProject(Project project, String username);
    Project findByProjectIdentifier(String projectIdentifier, String username);
    Iterable<Project> findAllProjects(String username);
    void deleteProjectByIdentifier(String projectIdentifier, String username);
    void checkIfProjectExist(Project project, String username);
}
