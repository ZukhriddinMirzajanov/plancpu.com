package com.plancpu.backend.service;

import com.plancpu.backend.entity.User;
import com.plancpu.backend.repository.UserRepository;
import com.plancpu.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ManagerService {

    private final UserRepository userRepository;

    public List<User> getUsersByCompanyId(Long id) {
        List<User> filteredUsers = userRepository.findAll().stream().filter(user -> (user.getCompany().getId() == id && user.getRole().name() != "MANAGER")).collect(Collectors.toList());
        return filteredUsers;

    }
}
