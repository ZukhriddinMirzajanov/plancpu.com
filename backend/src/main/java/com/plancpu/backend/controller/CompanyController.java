package com.plancpu.backend.controller;

import com.plancpu.backend.entity.Company;
import com.plancpu.backend.entity.CompanyProject;
import com.plancpu.backend.entity.Task;
import com.plancpu.backend.entity.User;
import com.plancpu.backend.repository.UserRepository;
import com.plancpu.backend.service.CompanyService;
import com.plancpu.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Company>> getCompanyById(@PathVariable("id") Long companyId) {
        Optional<Company> companyById = companyService.getCompanyById(companyId);
        if (companyById == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(companyById);
        }
    }

    @GetMapping("/getAll/{id}")
    public ResponseEntity<List<Company>> getAllCompany(@PathVariable("id") Long companyId) {
        return ResponseEntity.ok(companyService.getAllCompany().stream().filter(company -> company.getId() != companyId).collect(Collectors.toList()));
    }

    @PostMapping("/create")
    public ResponseEntity<Company> createTask(@RequestBody Company company) {
        return ResponseEntity.ok(companyService.createCompany(company));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable("id") Long id, @RequestBody Company updatedCompany) {
        Optional<Company> foundCompany = companyService.getCompanyById(id);

        if (foundCompany.isPresent()) {
            Company existingCompany = foundCompany.get();

            // Update the fields of the existing task with the values from the updated task
            existingCompany.setId(updatedCompany.getId());
            existingCompany.setName(updatedCompany.getName());
            existingCompany.setIsActive(updatedCompany.getIsActive());
            existingCompany.setJoinedDate(updatedCompany.getJoinedDate());

            // Save the updated task
            companyService.createCompany(existingCompany);

            return ResponseEntity.ok(existingCompany);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompanyById(@PathVariable("id") Long id, Authentication authentication) {
        Optional<Company> foundCompany = companyService.getCompanyById(id);
        if (foundCompany.isPresent()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            var user = userRepository.findByEmail(userDetails.getUsername());
            if (user.get().getRole().name().equals("ADMIN")) {
                companyService.delete(id);
                return ResponseEntity.ok(id);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this task.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
