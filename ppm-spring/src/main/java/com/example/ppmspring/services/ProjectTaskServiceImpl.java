package com.example.ppmspring.services;

import com.example.ppmspring.domain.Backlog;
import com.example.ppmspring.domain.ProjectTask;
import com.example.ppmspring.repositories.BacklogRepository;
import com.example.ppmspring.repositories.ProjectTaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectTaskServiceImpl implements  ProjectTaskService{

    private final ProjectTaskRepository projectTaskRepository;
    private final BacklogRepository backlogRepository;

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
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
    }

    @Override
    public Iterable<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
    }
}
