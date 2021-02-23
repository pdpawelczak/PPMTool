package com.example.ppmspring.controllers;

import com.example.ppmspring.domain.ProjectTask;
import com.example.ppmspring.services.MapValidationErrorService;
import com.example.ppmspring.services.ProjectTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
@AllArgsConstructor
public class BacklogController {

    private final ProjectTaskService projectTaskService;
    private final MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlogId}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult bindingResult, @PathVariable String backlogId){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null){
            return errorMap;
        }
        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlogId, projectTask);
        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }
}
