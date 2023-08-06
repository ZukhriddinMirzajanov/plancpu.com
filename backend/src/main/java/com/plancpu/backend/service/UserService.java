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

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Long updateUser(User user) {
        userRepository.save(user);
        return user.getId();
    }
}
