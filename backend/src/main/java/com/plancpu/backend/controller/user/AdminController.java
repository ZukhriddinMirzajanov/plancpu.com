package com.plancpu.backend.controller.user;

import com.plancpu.backend.entity.User;
import com.plancpu.backend.service.AdminService;
import com.plancpu.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/managers")
    public ResponseEntity<List<User>> getAllManagers() {
        if (adminService.getAllManagers() != null) {
            return ResponseEntity.ok(adminService.getAllManagers());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUserById(id);
            return ResponseEntity.ok("Id: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
