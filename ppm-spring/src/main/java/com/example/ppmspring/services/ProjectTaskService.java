package com.example.ppmspring.services;

import com.example.ppmspring.domain.ProjectTask;

public interface ProjectTaskService {
    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);
}
