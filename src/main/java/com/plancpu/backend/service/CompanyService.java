package com.plancpu.backend.service;

import com.plancpu.backend.entity.Company;
import com.plancpu.backend.entity.CompanyProject;
import com.plancpu.backend.entity.Task;
import com.plancpu.backend.entity.User;
import com.plancpu.backend.exception.NotFoundException;
import com.plancpu.backend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final ManagerService managerService;
    private final CompanyProjectService companyProjectService;
    private final UserService userService;

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Long delete(Long id) {
        // Retrieve the company by ID
        Optional<Company> companyOptional = companyRepository.findById(id);

        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();

            List<CompanyProject> companyProjects = companyProjectService.getAllCompanyProjectsByCompanyId(company.getId());
            for (CompanyProject companyProject : companyProjects) {
                companyProjectService.delete(companyProject.getId());
            }

            List<User> users = managerService.getAllUsersByCompanyId(company.getId());
            for (User user : users) {
                userService.deleteUserById(user.getId());
            }

            companyRepository.deleteById(id);
            return id;
        } else {
            throw new NotFoundException("Company not found with id: " + id);
        }
    }
}
