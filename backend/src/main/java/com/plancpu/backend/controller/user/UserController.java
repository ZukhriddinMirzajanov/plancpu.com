package com.plancpu.backend.controller.user;

import com.plancpu.backend.controller.AuthenticationResponse;
import com.plancpu.backend.controller.RegisterUserRequest;
import com.plancpu.backend.entity.User;
import com.plancpu.backend.service.AuthService;
import com.plancpu.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/profile/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(userService.getUserById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update{id}")
    public ResponseEntity<AuthenticationResponse> updateUser(@PathVariable("id") Long id, @RequestBody RegisterUserRequest request) {
        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setCompanyId(request.getCompanyId());
            user.setCompanyName(request.getCompanyName());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());

            userService.updateUser(user);

            // You might not need to return an AuthenticationResponse here, adjust as needed.
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
