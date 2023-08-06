package com.plancpu.backend.controller;

import com.plancpu.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {

    private Long companyId;
    private String companyName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
