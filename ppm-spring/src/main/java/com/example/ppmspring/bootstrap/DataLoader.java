package com.example.ppmspring.bootstrap;

import com.example.ppmspring.domain.Project;
import com.example.ppmspring.domain.User;
import com.example.ppmspring.repositories.ProjectRepository;
import com.example.ppmspring.repositories.UserRepository;
import com.example.ppmspring.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {
    
    public final ProjectRepository projectRepository;
    public final ProjectService projectService;
    public final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if(projectRepository.findAll().isEmpty()){
            System.out.println("---------Bootstrapping projects data---------");
            loadData();
        }
    }

    private void loadData() {
        Date startDate = new Date();
        Date endDate = new Date();

        User user = User.builder()
                .username("test@test.gmail.com")
                .password("test1234")
                .fullName("test user")
                .build();
        userRepository.save(user);

        IntStream.rangeClosed(1, 5)
                .forEach(i -> {
                    Project project = Project.builder()
                            .projectName("Project" + i)
                            .description("Project description" + i)
                            .projectIdentifier("PRO" + i)
                            .startDate(startDate)
                            .endDate(endDate)
                            .build();
                    projectService.saveOrUpdateProject(project, user.getUsername());
                });
    }
}
