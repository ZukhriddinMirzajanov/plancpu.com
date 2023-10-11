package com.plancpu.backend.controller;

import com.plancpu.backend.entity.Task;
import com.plancpu.backend.entity.TimeReport;
import com.plancpu.backend.entity.User;
import com.plancpu.backend.repository.UserRepository;
import com.plancpu.backend.service.TimeReportService;
import com.plancpu.backend.service.UserService;
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
    private final UserService userService;

    @GetMapping("/companyId/{id}")
    public ResponseEntity<List<TimeReport>> getAllTimeReportsByCompanyId(@PathVariable("id") Long companyId) {
        List<TimeReport> timeReportsByCompanyId = timeReportService.getAllTimeReportsByCompanyId(companyId);
        if (timeReportsByCompanyId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(timeReportsByCompanyId);
        }
    }

    @GetMapping("/userId/{id}")
    public ResponseEntity<List<TimeReport>> getAllTimeReportsByUserId(@PathVariable("id") Long userId) {
        List<TimeReport> timeReportsByUserId = timeReportService.getAllTimeReportsByUserId(userId);
        if (timeReportsByUserId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(timeReportsByUserId);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TimeReport>> getTimeReportById(@PathVariable("id") Long id) {
        Optional<TimeReport> timeReport = timeReportService.getTimeReportById(id);
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
        Optional<TimeReport> foundTimeReport = timeReportService.getTimeReportById(id);

        if (foundTimeReport.isPresent()) {
            TimeReport existingTimeReport = foundTimeReport.get();

            // Update the fields of the existing time report with the values from the updated time report
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

    @PutMapping("/{timeReportId}/user/{userId}")
    public TimeReport addUserToTimeReport(
            @PathVariable Long timeReportId,
            @PathVariable Long userId
    ) {
        TimeReport timeReport = timeReportService.getTimeReportById(timeReportId).get();
        User user = userService.getUserById(userId).get();
        timeReport.setUser(user);
        return timeReportService.createTimeReport(timeReport);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTimeReportById(@PathVariable("id") Long id, Authentication authentication) {
        Optional<TimeReport> foundTimrReport = timeReportService.getTimeReportById(id);
        if (foundTimrReport.isPresent()) {
            TimeReport timeReport = foundTimrReport.get();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            var user = userRepository.findByEmail(userDetails.getUsername());
            if (
                    userDetails.getUsername().equals(timeReport.getUser().getEmail()) ||
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
