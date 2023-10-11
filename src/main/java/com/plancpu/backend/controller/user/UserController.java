package com.plancpu.backend.controller.user;

import com.plancpu.backend.controller.AuthenticationResponse;
import com.plancpu.backend.controller.RegisterUserRequest;
import com.plancpu.backend.entity.Company;
import com.plancpu.backend.entity.CompanyProject;
import com.plancpu.backend.entity.User;
import com.plancpu.backend.service.AuthService;
import com.plancpu.backend.service.CompanyProjectService;
import com.plancpu.backend.service.CompanyService;
import com.plancpu.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;
    private final CompanyProjectService companyProjectService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(userService.getUserById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}/company/{companyId}")
        public ResponseEntity<User> addCompanyToUser(@PathVariable("userId") Long userId, @PathVariable("companyId") Long companyId) {
        Company company = companyService.getCompanyById(companyId).orElseThrow(() -> new EntityNotFoundException("Company not found"));
        User user = userService.getUserById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setCompany(company);
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}/companyProject/{companyProjectId}")
    public User addCompanyProjectToUser(@PathVariable Long userId, @PathVariable Long companyProjectId) {
        CompanyProject companyProject = companyProjectService.getById(companyProjectId).orElseThrow(() -> new EntityNotFoundException("CompanyProject not found"));
        User user = userService.getUserById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.getCompanyProjects().add(companyProject);
        userService.updateUser(user);
        return user;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody RegisterUserRequest request) {
        Optional<User> userOptional = userService.getUserById(id);
        boolean isExists = authService.isEmailAlreadyRegistered(request.getEmail());
        User user = userOptional.get();

        if (userOptional.isPresent() && !isExists) {

            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            user.setRole(request.getRole());
            user.setCompany(request.getCompany());
            user.setCompanyProjects(request.getCompanyProjects());

            userService.updateUser(user);

            return ResponseEntity.ok(user);
        } else if (isExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping("/{userId}/company/{compnayId}")
//    public User addUserToCompany(
//            @PathVariable Long userId,
//            @PathVariable Long compnayId
//    ) {
//        User User = userService.getUserById(userId).get();
//        Teacher teacher = teacherRepository.findById(compnayId).get();
//        subject.setTeacher(teacher);
//        return subjectRepository.save(subject);
//    }
}
