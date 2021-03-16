package com.example.ppmspring.controllers;

import com.example.ppmspring.domain.Project;
import com.example.ppmspring.services.MapValidationErrorService;
import com.example.ppmspring.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid  @RequestBody Project project, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }
        projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectIdentifier){
        Project project = projectService.findByProjectIdentifier(projectIdentifier);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> findAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("{projectIdentifier}")
    public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectIdentifier){
        projectService.deleteProjectByIdentifier(projectIdentifier);
        return new ResponseEntity<String>("Project with ID: " +projectIdentifier + " was deleted!", HttpStatus.OK);
    }
}
