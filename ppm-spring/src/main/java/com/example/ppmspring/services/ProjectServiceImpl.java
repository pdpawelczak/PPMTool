package com.example.ppmspring.services;

import com.example.ppmspring.domain.Project;
import com.example.ppmspring.exceptions.ProjectIdentifierException;
import com.example.ppmspring.exceptions.ProjectsNotFoundException;
import com.example.ppmspring.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdentifierException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exists!");
        }
    }

    @Override
    public Project findByProjectIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null){
            throw new ProjectIdentifierException("Project with ID: " + projectIdentifier + " does not exists!");
        }
        return project;
    }

    @Override
    public Iterable<Project> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        if(projects.isEmpty()){
            throw new ProjectsNotFoundException("No projects found!");
        }
        return projectRepository.findAll();
    }
}
