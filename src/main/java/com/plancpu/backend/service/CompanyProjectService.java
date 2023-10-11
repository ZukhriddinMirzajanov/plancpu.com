package com.plancpu.backend.service;

import com.plancpu.backend.entity.Company;
import com.plancpu.backend.entity.CompanyProject;
import com.plancpu.backend.entity.User;
import com.plancpu.backend.repository.CompanyProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompanyProjectService {

    private final CompanyProjectRepository companyProjectRepository;
    private final ManagerService managerService;
    private final UserService userService;

    public Optional<CompanyProject> getById(Long id) {
        return companyProjectRepository.findById(id);
    }

    public List<CompanyProject> getAllCompanyProjectsByCompanyId(Long companyId) {
        List<CompanyProject> companyProjects = companyProjectRepository.findAll();
        if (companyProjects.size() > 0) {
            return companyProjectRepository.findAll()
                    .stream()
                    .filter(companyProject -> {
                        Company company = companyProject.getCompany();
                        return company != null && company.getId() != null && company.getId().equals(companyId);
                    })
                    .collect(Collectors.toList());
        } else {
            return companyProjectRepository.findAll();
        }
    }

    public CompanyProject create(CompanyProject companyProject) {
        return companyProjectRepository.save(companyProject);
    }

    public Long delete(Long id) {
        CompanyProject companyProject = companyProjectRepository.findById(id).get();
        Company company = companyProject.getCompany();
        List<User> users = managerService.getUsersByCompanyId(company.getId());
        for (User user : users) {
            user.getCompanyProjects().remove(companyProject);
            userService.updateUser(user);
        }
        companyProjectRepository.deleteById(id);
        return id;
    }
}
