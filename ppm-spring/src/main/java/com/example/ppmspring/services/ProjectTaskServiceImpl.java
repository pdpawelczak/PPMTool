package com.example.ppmspring.services;

import com.example.ppmspring.domain.Backlog;
import com.example.ppmspring.domain.Project;
import com.example.ppmspring.domain.ProjectTask;
import com.example.ppmspring.exceptions.ProjectNotFoundException;
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
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);

            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

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
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '" + projectIdentifier + "' does not exist");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
    }
}
