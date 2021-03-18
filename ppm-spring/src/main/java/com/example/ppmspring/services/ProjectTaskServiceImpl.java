package com.example.ppmspring.services;

import com.example.ppmspring.domain.Backlog;
import com.example.ppmspring.domain.Project;
import com.example.ppmspring.domain.ProjectTask;
import com.example.ppmspring.exceptions.ProjectNotFoundException;
import com.example.ppmspring.exceptions.ProjectsNotFoundException;
import com.example.ppmspring.repositories.ProjectTaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProjectTaskServiceImpl implements  ProjectTaskService{

    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectService projectService;

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
            Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier, username).getBacklog();
            projectTask.setBacklog(backlog);

            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectIdentifier.toUpperCase() + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());

            if(projectTask.getPriority() == null || projectTask.getPriority() == 0){
                projectTask.setPriority(3);
            }
            if(projectTask.getStatus() == null || projectTask.getStatus() == ""){
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);
    }

    @Override
    public Iterable<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier, String username) {
        projectService.findByProjectIdentifier(projectIdentifier, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier.toUpperCase());
    }

    @Override
    public ProjectTask findProjectTaskByProjectSequence(String projectIdentifier, String ptId, String username) {
        String projectIdentifierUpper = projectIdentifier.toUpperCase();
        String ptIdUpper = ptId.toUpperCase();

        projectService.findByProjectIdentifier(projectIdentifier, username);
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(ptIdUpper);
        if(projectTask == null){
            throw new ProjectNotFoundException("Project task " + ptIdUpper + " not found");
        }
        if(!projectTask.getProjectIdentifier().equals(projectIdentifierUpper)){
            throw new ProjectNotFoundException("Project Task " + ptIdUpper + " does not exist in project: " + projectIdentifierUpper);
        }
        return projectTask;
    }

    @Override
    public ProjectTask updateProjectTaskByProjectSequence(ProjectTask updatedTask, String projectIdentifier, String ptId,
                                                          String username) {
        findProjectTaskByProjectSequence(projectIdentifier, ptId, username);
        if(updatedTask.getPriority() < 1 || updatedTask.getPriority() > 3){
            throw new ProjectsNotFoundException("Task priority " +updatedTask.getPriority() + " cannot be applied. Project priority values can be from 1 to 3!");
        }
        return projectTaskRepository.save(updatedTask);
    }

    @Override
    public void deleteProjectTaskByProjectSequence(String projectIdentifier, String ptId, String username) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(projectIdentifier, ptId, username);
        projectTaskRepository.delete(projectTask);
    }
}
