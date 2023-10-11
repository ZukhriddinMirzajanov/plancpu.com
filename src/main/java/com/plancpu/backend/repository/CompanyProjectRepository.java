package com.plancpu.backend.repository;

import com.plancpu.backend.entity.CompanyProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyProjectRepository extends JpaRepository<CompanyProject, Long> {
}
