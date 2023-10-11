package com.plancpu.backend.controller;

import com.plancpu.backend.entity.Company;
import com.plancpu.backend.entity.CompanyProject;
import com.plancpu.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Company company;
    private List<CompanyProject> companyProjects;
}
