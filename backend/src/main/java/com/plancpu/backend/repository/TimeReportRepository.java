package com.plancpu.backend.repository;

import com.plancpu.backend.entity.TimeReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeReportRepository extends JpaRepository<TimeReport, Long> {
    List<TimeReport> findByCompanyId(Long id);
}
