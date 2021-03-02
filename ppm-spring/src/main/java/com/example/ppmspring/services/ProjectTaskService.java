package com.example.ppmspring.services;

import com.example.ppmspring.domain.ProjectTask;

public interface ProjectTaskService {
    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);
    Iterable<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier);
    ProjectTask findProjectTaskByProjectSequence(String projectIdentifier, String ptId);
    ProjectTask updateProjectTaskByProjectSequence(ProjectTask updatedTask, String projectIdentifier, String ptId);
    void deleteProjectTaskByProjectSequence(String projectIdentifier, String ptId);
}
