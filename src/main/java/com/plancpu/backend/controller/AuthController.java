package com.plancpu.backend.controller;

import com.plancpu.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerManager(@RequestBody RegisterUserRequest request) {
        boolean isExists = authService.isEmailAlreadyRegistered(request.getEmail());

        if (isExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginManager(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
