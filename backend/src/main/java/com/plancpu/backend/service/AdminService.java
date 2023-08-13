package com.plancpu.backend.service;

import com.plancpu.backend.entity.User;
import com.plancpu.backend.repository.UserRepository;
import com.plancpu.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllManagers() {
        List<User> users = userRepository.findByRole("MANAGER");
        if (users != null) {
            return users;
        } else {
            return null;
        }
    }
}
