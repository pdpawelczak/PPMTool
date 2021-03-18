package com.example.ppmspring.services;

import com.example.ppmspring.domain.Backlog;
import com.example.ppmspring.domain.Project;
import com.example.ppmspring.domain.User;
import com.example.ppmspring.exceptions.ProjectIdentifierException;
import com.example.ppmspring.exceptions.ProjectNotFoundException;
import com.example.ppmspring.exceptions.ProjectsNotFoundException;
import com.example.ppmspring.repositories.BacklogRepository;
import com.example.ppmspring.repositories.ProjectRepository;
import com.example.ppmspring.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;
    private final UserRepository userRepository;

    @Override
    public Project saveOrUpdateProject(Project project, String username){
        String projectIdentifier = project.getProjectIdentifier().toUpperCase();
        User user = userRepository.findByUsername(username);
        checkIfProjectExist(project, username);

        try{
            project.setUser(user);
            project.setProjectLeader(user.getFullName());
            project.setProjectIdentifier(projectIdentifier);
            if(project.getId() == null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifier);
            }
            if(project.getId() != null){
                project.setCreatedAt(project.getCreatedAt());
                project.setUpdatedAt(new Date());
                project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
            }
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdentifierException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exists!");
        }
    }

    @Override
    public Project findByProjectIdentifier(String projectIdentifier, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        User user = userRepository.findByUsername(username);
        if(project == null){
            throw new ProjectIdentifierException("Project with ID: " + projectIdentifier + " does not exists!");
        }
        if(!project.getUser().equals(user)){
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return project;
    }

    @Override
    public Iterable<Project> findAllProjects(String username) {
        Iterable<Project> projects = projectRepository.findAllByUser(userRepository.findByUsername(username));
        if(projects == null){
            throw new ProjectsNotFoundException("No projects found!");
        }
        return projects;
    }

    @Override
    public void deleteProjectByIdentifier(String projectIdentifier, String username) {
        projectRepository.delete(findByProjectIdentifier(projectIdentifier, username));
    }

    @Override
    public void checkIfProjectExist(Project project, String username) {
        String projectIdentifier = project.getProjectIdentifier().toUpperCase();
        User user = userRepository.findByUsername(username);

        if(project.getId() != null){
            Optional<Project> existingProject = projectRepository.findById(project.getId());
            if(existingProject != null && (!existingProject.get().getUser().equals(user))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID: " + projectIdentifier + " does not exists!");
            }
        }
    }
}
