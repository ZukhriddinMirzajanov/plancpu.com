package com.plancpu.backend.controller;

import com.plancpu.backend.entity.Task;
import com.plancpu.backend.repository.UserRepository;
import com.plancpu.backend.service.TaskService;
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
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @GetMapping("/companyId/{id}")
    public ResponseEntity<List<Task>> getAllTasksByCompanyId(@PathVariable("id") Long companyId) {
        List<Task> tasksByCompanyId = taskService.getAllTasksByCompanyId(companyId);
        if (tasksByCompanyId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(tasksByCompanyId);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable("id") Long id) {
        Optional<Task> task = taskService.findTaskById(id);
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
        Optional<Task> foundTask = taskService.findTaskById(id);

        if (foundTask.isPresent()) {
            Task existingTask = foundTask.get();

            // Update the fields of the existing task with the values from the updated task
            existingTask.setCompanyId(updatedTask.getCompanyId());
            existingTask.setCreatedByEmail(updatedTask.getCreatedByEmail());
            existingTask.setCreatedByName(updatedTask.getCreatedByName());
            existingTask.setAssignedBy(updatedTask.getAssignedBy());
            existingTask.setTaskReviewer(updatedTask.getTaskReviewer());
            existingTask.setName(updatedTask.getName());
            existingTask.setHour(updatedTask.getHour());
            existingTask.setCreatedAt(updatedTask.getCreatedAt());
            existingTask.setStatusOfTask(updatedTask.getStatusOfTask());
            existingTask.setDescription(updatedTask.getDescription());

            // Save the updated task
            taskService.createTask(existingTask);

            return ResponseEntity.ok(existingTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable("id") Long id, Authentication authentication) {
        Optional<Task> foundTask = taskService.findTaskById(id);
        if (foundTask.isPresent()) {
            Task task = foundTask.get();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            var user = userRepository.findByEmail(userDetails.getUsername());
            if (
                    userDetails.getUsername().equals(task.getCreatedByEmail()) ||
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
