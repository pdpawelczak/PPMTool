package com.example.ppmspring.services;

import com.example.ppmspring.domain.Project;
import com.example.ppmspring.exceptions.ProjectIdException;
import com.example.ppmspring.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exists!");
        }
    }
}
