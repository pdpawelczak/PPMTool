package com.example.ppmspring.services;

import com.example.ppmspring.domain.Backlog;
import com.example.ppmspring.domain.Project;
import com.example.ppmspring.domain.ProjectTask;
import com.example.ppmspring.exceptions.ProjectNotFoundException;
import com.example.ppmspring.exceptions.ProjectsNotFoundException;
import com.example.ppmspring.repositories.BacklogRepository;
import com.example.ppmspring.repositories.ProjectRepository;
import com.example.ppmspring.repositories.ProjectTaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectTaskServiceImpl implements  ProjectTaskService{

    private final ProjectTaskRepository projectTaskRepository;
    private final BacklogRepository backlogRepository;
    private final ProjectRepository projectRepository;

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try{
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
            projectTask.setBacklog(backlog);

            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectIdentifier.toUpperCase() + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());

            if(projectTask.getPriority() == null){
                projectTask.setPriority(3);
            }
            if(projectTask.getStatus() == null || projectTask.getStatus() == ""){
                projectTask.setStatus("TODO");
            }
            return projectTaskRepository.save(projectTask);
        }catch (Exception e){
            throw new ProjectNotFoundException("Project not found!");
        }
    }

    @Override
    public Iterable<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '" + projectIdentifier.toUpperCase() + "' does not exist");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier.toUpperCase());
    }

    @Override
    public ProjectTask findProjectTaskByProjectSequence(String projectIdentifier, String ptId) {
        String projectIdentifierUpper = projectIdentifier.toUpperCase();
        String ptIdUpper = ptId.toUpperCase();

        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifierUpper);
        if(backlog == null){
            throw new ProjectNotFoundException("Project with ID: '" + projectIdentifierUpper + "' does not exist");
        }
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
    public ProjectTask updateProjectTaskByProjectSequence(ProjectTask updatedTask, String projectIdentifier, String ptId) {
        if(updatedTask.getPriority() < 1 || updatedTask.getPriority() > 3){
            throw new ProjectsNotFoundException("Task priority " +updatedTask.getPriority() + " cannot be applied. Project priority values can be from 1 to 3!");
        }
        ProjectTask projectTask = findProjectTaskByProjectSequence(projectIdentifier, ptId);
        return projectTaskRepository.save(projectTask);
    }
}
