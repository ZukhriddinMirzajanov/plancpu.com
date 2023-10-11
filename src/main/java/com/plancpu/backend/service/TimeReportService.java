package com.plancpu.backend.service;

import com.plancpu.backend.entity.TimeReport;
import com.plancpu.backend.repository.TimeReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TimeReportService {
    private final TimeReportRepository timeReportRepository;

    public Optional<TimeReport> getTimeReportById(Long id) {
        return timeReportRepository.findById(id);
    }

    public List<TimeReport> getAllTimeReportsByCompanyId(Long companyId) {
        List<TimeReport> filteredTimeReports = timeReportRepository.findAll().stream().filter(timeReport -> timeReport.getUser().getCompany().getId() == companyId).collect(Collectors.toList());
        return filteredTimeReports;
    }

    public List<TimeReport> getAllTimeReportsByUserId(Long userId) {
        List<TimeReport> filteredTimeReports = timeReportRepository.findAll()
                .stream().filter(timeReport -> timeReport.getUser().getId() == userId)
                .collect(Collectors.toList());
        return filteredTimeReports;
    }

    public TimeReport createTimeReport(TimeReport timeReport) {
        return timeReportRepository.save(timeReport);
    }

    public Long delete(Long id) {
        TimeReport timeReport = timeReportRepository.findById(id).get();
        timeReport.setUser(null);
        timeReportRepository.deleteById(id);
        return id;
    }
}
