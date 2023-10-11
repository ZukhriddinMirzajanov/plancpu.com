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
@Table(name = "app_task_comment")
public class TaskComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "comment field is required")
    private Long taskId;

    @NotBlank(message = "comment field is required")
    private String comment;

    @NotBlank(message = "commentedUserName field is required")
    private String commentedUserFullName;

    @NotBlank(message = "commentedUserName field is required")
    private String commentedUserEmail;

    @NotBlank(message = "commentedDate field is required")
    private Date commentedDate;
}
