package com.example.ppmspring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProjectTask extends BaseEntity{

    @Column(updatable = false, unique = true)
    private String projectSequence;

    @NotBlank(message = "Please include a project summary")
    private String summary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;

    private String acceptanceCriteria;
    private String status;
    private Integer priority;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dueDate;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;

    @Column(updatable = false)
    private String projectIdentifier;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
