package com.plancpu.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name is required")
    private String name;

    @NotBlank(message = "isActive field is required")
    private Boolean isActive;

    @NotBlank(message = "joinedDate field is required")
    private Date joinedDate;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
//    private List<CompanyProject> companyProjects;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
//    private List<User> users;
}
