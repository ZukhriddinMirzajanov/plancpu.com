package com.plancpu.backend.service;

import com.plancpu.backend.entity.Company;
import com.plancpu.backend.entity.Task;
import com.plancpu.backend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

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
        companyRepository.deleteById(id);
        return id;
    }
}
