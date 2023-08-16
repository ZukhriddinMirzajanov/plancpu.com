package com.plancpu.backend.service;

import com.plancpu.backend.entity.TimeReport;
import com.plancpu.backend.repository.TimeReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TimeReportService {
    private final TimeReportRepository timeReportRepository;

    public Optional<TimeReport> findTimeReportById(Long id) {
        return timeReportRepository.findById(id);
    }

    public List<TimeReport> getAllTimeReportsByCompanyId(Long companyId) {
        return timeReportRepository.findByCompanyId(companyId);
    }

    public TimeReport createTimeReport(TimeReport timeReport) {
        return timeReportRepository.save(timeReport);
    }

    public Long delete(Long id) {
        timeReportRepository.deleteById(id);
        return id;
    }
}
