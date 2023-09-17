package com.plancpu.backend.controller;

import com.plancpu.backend.entity.Company;
import com.plancpu.backend.entity.CompanyProject;
import com.plancpu.backend.entity.Task;
import com.plancpu.backend.entity.User;
import com.plancpu.backend.repository.UserRepository;
import com.plancpu.backend.service.CompanyProjectService;
import com.plancpu.backend.service.TaskService;
import com.plancpu.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CompanyProjectService companyProjectService;

    @GetMapping("/companyProjectId/{id}")
    public ResponseEntity<List<Task>> getAllTasksByCompanyProjectId(@PathVariable("id") Long companyProjectId) {
        List<Task> tasksByCompanyProjectId = taskService.getAllTasksByCompanyProjectId(companyProjectId);
        return ResponseEntity.ok(tasksByCompanyProjectId);
    }

    @GetMapping("/statusZeroTasksByCompanyProjectId/{id}")
    public ResponseEntity<List<Task>> getAllStatusZeroTasksByCompanyProjectId(@PathVariable("id") Long companyProjectId) {
        List<Task> tasksByCompanyProjectId = taskService.getAllStatusZeroTasksByCompanyProjectId(companyProjectId);
        return ResponseEntity.ok(tasksByCompanyProjectId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable("id") Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(task);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task updatedTask) {
        Optional<Task> foundTask = taskService.getTaskById(id);

        if (foundTask.isPresent()) {
            Task existingTask = foundTask.get();

            existingTask.setName(updatedTask.getName());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setHour(updatedTask.getHour());
            existingTask.setStatusOfTask(updatedTask.getStatusOfTask());

            taskService.createTask(existingTask);

            return ResponseEntity.ok(existingTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{taskId}/userCreated/{userCreatedId}")
    public Task addUserCreatedToTask(
            @PathVariable Long taskId,
            @PathVariable Long userCreatedId
    ) {
        Task task = taskService.getTaskById(taskId).get();
        User user = userService.getUserById(userCreatedId).get();
        task.setUserCreated(user);
        return taskService.createTask(task);
    }

    @PutMapping("/{taskId}/userWorked/{userWorkedId}")
    public Task addUserWorkedToTask(
            @PathVariable Long taskId,
            @PathVariable Long userWorkedId
    ) {
        Task task = taskService.getTaskById(taskId).get();
        User user = userService.getUserById(userWorkedId).get();
        task.setUserWorked(user);
        return taskService.createTask(task);
    }

    @PutMapping("/{taskId}/userReviewed/{userReviewedId}")
    public Task addUserReviewedToTask(
            @PathVariable Long taskId,
            @PathVariable Long userReviewedId
    ) {
        Task task = taskService.getTaskById(taskId).get();
        User user = userService.getUserById(userReviewedId).get();
        task.setUserReviewed(user);
        return taskService.createTask(task);
    }

    @PutMapping("/{taskId}/companyProject/{companyProjectId}")
    public Task addCompanyProjectToTask(
            @PathVariable Long taskId,
            @PathVariable Long companyProjectId
    ) {
        Task task = taskService.getTaskById(taskId).get();
        CompanyProject companyProject = companyProjectService.getById(companyProjectId).get();
        task.setCompanyProject(companyProject);
        return taskService.createTask(task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable("id") Long id, Authentication authentication) {
        Optional<Task> foundTask = taskService.getTaskById(id);
        if (foundTask.isPresent()) {
            Task task = foundTask.get();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            var user = userRepository.findByEmail(userDetails.getUsername());
            if (
                    userDetails.getUsername().equals(task.getUserCreated().getEmail()) ||
                    user.get().getRole().name().equals("MANAGER") ||
                    user.get().getRole().name().equals("ADMIN")
            ) {
                taskService.delete(id);
                return ResponseEntity.ok(id);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this task.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
