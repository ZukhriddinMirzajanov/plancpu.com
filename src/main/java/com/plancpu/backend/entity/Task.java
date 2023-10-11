package com.plancpu.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name field is required")
    private String name;

    @NotBlank(message = "description field is required")
    private String description;

    @NotBlank(message = "hour field is required")
    private int hour;

    @NotBlank(message = "createdAt field is required")
    private Date createdAt;

    @NotBlank(message = "statusOfTask field is required")
    private int statusOfTask;

    @NotBlank(message = "finishedDate field is required")
    private Date lastUpdatedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userCreated_id")
    private User userCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userWorked_id")
    private User userWorked;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userReviewed_id")
    private User userReviewed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyProject_id")
    private CompanyProject companyProject;
}
