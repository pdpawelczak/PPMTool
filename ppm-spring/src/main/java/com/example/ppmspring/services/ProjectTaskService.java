package com.example.ppmspring.services;

import com.example.ppmspring.domain.ProjectTask;

import java.security.Principal;

public interface ProjectTaskService {
    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username);
    Iterable<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier, String username);
    ProjectTask findProjectTaskByProjectSequence(String projectIdentifier, String ptId, String username);
    ProjectTask updateProjectTaskByProjectSequence(ProjectTask updatedTask, String projectIdentifier, String ptId, String username);
    void deleteProjectTaskByProjectSequence(String projectIdentifier, String ptId, String username);
}
