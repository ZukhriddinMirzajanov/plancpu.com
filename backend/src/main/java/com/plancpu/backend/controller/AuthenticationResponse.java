package com.plancpu.backend.controller;

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
    private Long companyId;
    private String companyName;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String token;
}
