package com.plancpu.backend.repository;

import com.plancpu.backend.entity.TimeReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeReportRepository extends JpaRepository<TimeReport, Long> {
}
