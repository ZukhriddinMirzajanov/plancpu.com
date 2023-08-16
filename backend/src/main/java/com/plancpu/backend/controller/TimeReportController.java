package com.plancpu.backend.controller;

import com.plancpu.backend.entity.TimeReport;
import com.plancpu.backend.repository.UserRepository;
import com.plancpu.backend.service.TimeReportService;
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
@RequestMapping("/api/time-reports")
public class TimeReportController {

    private final TimeReportService timeReportService;
    private final UserRepository userRepository;

    @GetMapping("/companyId/{id}")
    public ResponseEntity<List<TimeReport>> getAllTimeReportsByCompanyId(@PathVariable("id") Long companyId) {
        List<TimeReport> timeReportsByCompanyId = timeReportService.getAllTimeReportsByCompanyId(companyId);
        if (timeReportsByCompanyId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(timeReportsByCompanyId);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TimeReport>> getTimeReportById(@PathVariable("id") Long id) {
        Optional<TimeReport> timeReport = timeReportService.findTimeReportById(id);
        if (timeReport == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(timeReport);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<TimeReport> createTimeReport(@RequestBody TimeReport timeReport) {
        return ResponseEntity.ok(timeReportService.createTimeReport(timeReport));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TimeReport> updateTimeReport(@PathVariable("id") Long id, @RequestBody TimeReport updatedTimeReport) {
        Optional<TimeReport> foundTimeReport = timeReportService.findTimeReportById(id);

        if (foundTimeReport.isPresent()) {
            TimeReport existingTimeReport = foundTimeReport.get();

            // Update the fields of the existing time report with the values from the updated time report
            existingTimeReport.setCompanyId(updatedTimeReport.getCompanyId());
            existingTimeReport.setCreatedByEmail(updatedTimeReport.getCreatedByEmail());
            existingTimeReport.setCreatedByName(updatedTimeReport.getCreatedByName());
            existingTimeReport.setTitle(updatedTimeReport.getTitle());
            existingTimeReport.setHour(updatedTimeReport.getHour());
            existingTimeReport.setCreatedAt(updatedTimeReport.getCreatedAt());

            // Save the updated time report
            timeReportService.createTimeReport(existingTimeReport);

            return ResponseEntity.ok(existingTimeReport);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTimeReportById(@PathVariable("id") Long id, Authentication authentication) {
        Optional<TimeReport> foundTimrReport = timeReportService.findTimeReportById(id);
        if (foundTimrReport.isPresent()) {
            TimeReport timeReport = foundTimrReport.get();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            var user = userRepository.findByEmail(userDetails.getUsername());
            if (
                    userDetails.getUsername().equals(timeReport.getCreatedByEmail()) ||
                            user.get().getRole().name().equals("MANAGER") ||
                            user.get().getRole().name().equals("ADMIN")
            ) {
                timeReportService.delete(id);
                return ResponseEntity.ok(id);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete!");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
