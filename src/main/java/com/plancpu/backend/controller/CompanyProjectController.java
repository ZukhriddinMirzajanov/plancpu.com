package com.plancpu.backend.controller;

import com.plancpu.backend.entity.Company;
import com.plancpu.backend.entity.CompanyProject;
import com.plancpu.backend.entity.Task;
import com.plancpu.backend.entity.User;
import com.plancpu.backend.repository.UserRepository;
import com.plancpu.backend.service.CompanyProjectService;
import com.plancpu.backend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/companyProject")
public class CompanyProjectController {

    private final CompanyProjectService companyProjectService;
    private final CompanyService  companyService;
    private final UserRepository userRepository;

    @GetMapping("/getAllByCompanyId/{id}")
    public ResponseEntity<List<CompanyProject>> getAllCompanyProjectsByCompanyId(@PathVariable("id") Long companyId) {
        return ResponseEntity.ok(companyProjectService.getAllCompanyProjectsByCompanyId(companyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CompanyProject>> getById(@PathVariable("id") Long companyId) {
        Optional<CompanyProject> companyById = companyProjectService.getById(companyId);
        if (companyById == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(companyById);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CompanyProject> create(@RequestBody CompanyProject companyProject) {
        return ResponseEntity.ok(companyProjectService.create(companyProject));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyProject> update(@PathVariable("id") Long id, @RequestBody CompanyProject updatedCompanyProject) {
        Optional<CompanyProject> foundCompanyProject = companyProjectService.getById(id);

        if (foundCompanyProject.isPresent()) {
            CompanyProject existingCompanyProject = foundCompanyProject.get();

            if (updatedCompanyProject.getId() != null
                    && updatedCompanyProject.getName() != null
                    && updatedCompanyProject.getDescription() != null
                    && updatedCompanyProject.getCreatedDate() != null
                    && updatedCompanyProject.getFinishedDate() != null
                    && updatedCompanyProject.getCompany() != null) {
                // Update the fields of the existing task with the values from the updated task
                existingCompanyProject.setId(updatedCompanyProject.getId());
                existingCompanyProject.setName(updatedCompanyProject.getName());
                existingCompanyProject.setDescription(updatedCompanyProject.getDescription());
                existingCompanyProject.setCreatedDate(updatedCompanyProject.getCreatedDate());
                existingCompanyProject.setFinishedDate(updatedCompanyProject.getFinishedDate());
                existingCompanyProject.setCompany(updatedCompanyProject.getCompany());

                // Save the updated task
                companyProjectService.create(existingCompanyProject);

                return ResponseEntity.ok(existingCompanyProject);
            } else {
                return ResponseEntity.badRequest().build();
            }

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{companyProjectId}/company/{companyId}")
    public CompanyProject addCompanyToCompanyProject(
            @PathVariable Long companyProjectId,
            @PathVariable Long companyId
    ) {
        Company company = companyService.getCompanyById(companyId).get();
        CompanyProject companyProject = companyProjectService.getById(companyProjectId).get();
        companyProject.setCompany(company);
        return companyProjectService.create(companyProject);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompanyProjectById(@PathVariable("id") Long id, Authentication authentication) {
        Optional<CompanyProject> foundCompanyProject = companyProjectService.getById(id);
        if (foundCompanyProject.isPresent()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            var user = userRepository.findByEmail(userDetails.getUsername());
            if (user.get().getRole().name().equals("ADMIN") || user.get().getRole().name().equals("MANAGER")) {
                companyProjectService.delete(id);
                return ResponseEntity.ok(id);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this task.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
