package com.plancpu.backend.controller.user;

import com.plancpu.backend.entity.Role;
import com.plancpu.backend.entity.User;
import com.plancpu.backend.service.ManagerService;
import com.plancpu.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;
    private final UserService userService;

    @GetMapping("/users-by-companyId/{id}")
    public ResponseEntity<List<User>> getUsersByCompanyId(@PathVariable("id") Long companyId) {
        List<User> userByCompanyId = managerService.getUsersByCompanyId(companyId);
        if (userByCompanyId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userByCompanyId);
        }
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        Role role = user.get().getRole();
        if (user.isPresent()) {
            if(role.name() == "USER") {
                userService.deleteUserById(id);
                return ResponseEntity.ok("Id: " + id);
            } else {
                return ResponseEntity.badRequest().build();
            }

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
