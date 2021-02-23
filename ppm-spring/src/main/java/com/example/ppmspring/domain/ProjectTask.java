package com.example.ppmspring.domain;

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

    @Column(updatable = false)
    private String projectSequence;

    @NotBlank(message = "Please include a project summary")
    private String summary;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;

    private String acceptanceCriteria;
    private String status;
    private Integer priority;
    private Date dueDate;

    @Column(updatable = false)
    private Date create_At;
    private Date update_At;

    @Column(updatable = false)
    private String projectIdentifier;

    @PrePersist
    protected void onCreate(){
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.update_At = new Date();
    }
}
