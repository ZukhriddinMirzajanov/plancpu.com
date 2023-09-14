package com.plancpu.backend.controller;

import com.plancpu.backend.entity.Company;
import com.plancpu.backend.entity.CompanyProject;
import com.plancpu.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String token;
    private Company company;
    private CompanyProject companyProjects;
}
