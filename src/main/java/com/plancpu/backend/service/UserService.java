package com.plancpu.backend.service;

import com.plancpu.backend.controller.AuthenticationResponse;
import com.plancpu.backend.controller.LoginRequest;
import com.plancpu.backend.controller.RegisterUserRequest;
import com.plancpu.backend.entity.Role;
import com.plancpu.backend.entity.User;
import com.plancpu.backend.repository.UserRepository;
import com.plancpu.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).get();
        user.setCompany(null);
        user.setCompanyProjects(null);
        userRepository.save(user);
        userRepository.deleteById(id);
    }

    public AuthenticationResponse updateUser(User user) {
        userRepository.save(user);
        var jwtToken = jwtUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }
}
