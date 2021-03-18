package com.example.ppmspring.repositories;

import com.example.ppmspring.domain.Project;
import com.example.ppmspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByProjectIdentifier(String projectId);

    Iterable<Project> findAllByUser(User user);
}
