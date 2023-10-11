package com.plancpu.backend.controller;

import com.plancpu.backend.entity.Task;
import com.plancpu.backend.entity.TaskComment;
import com.plancpu.backend.repository.UserRepository;
import com.plancpu.backend.service.TaskCommentService;
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
@RequestMapping("/api/taskComment")
public class TaskCommentController {
    private final TaskCommentService taskCommentService;
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TaskComment>> getTaskCommentById(@PathVariable("id") Long id) {
        Optional<TaskComment> taskComment = taskCommentService.getTaskCommentById(id);
        if (taskComment == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(taskComment);
        }
    }

    @GetMapping("/byTaskId/{id}")
    public ResponseEntity<List<TaskComment>> getAllTaskCommentsByTaskId(@PathVariable("id") Long taskId) {
        List<TaskComment> commentsByTaskId = taskCommentService.getAllTaskCommentsByTaskId(taskId);
        return ResponseEntity.ok(commentsByTaskId);
    }

    @PostMapping("/create")
    public ResponseEntity<TaskComment> createTaskComment(@RequestBody TaskComment taskComment) {
        return ResponseEntity.ok(taskCommentService.createTaskComment(taskComment));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskComment> updateTaskComment(@PathVariable("id") Long id, @RequestBody TaskComment updatedTaskComment) {
        Optional<TaskComment> foundTaskComment = taskCommentService.getTaskCommentById(id);

        if (foundTaskComment.isPresent()) {
            TaskComment existingTaskComment = foundTaskComment.get();

            existingTaskComment.setComment(updatedTaskComment.getComment());

            taskCommentService.createTaskComment(existingTaskComment);

            return ResponseEntity.ok(existingTaskComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTaskCommentById(@PathVariable("id") Long id, Authentication authentication) {
        Optional<TaskComment> foundTaskComment = taskCommentService.getTaskCommentById(id);
        if (foundTaskComment.isPresent()) {
            TaskComment taskComment = foundTaskComment.get();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            var user = userRepository.findByEmail(userDetails.getUsername());
            if (
                    userDetails.getUsername().equals(taskComment.getCommentedUserEmail()) ||
                            user.get().getRole().name().equals("MANAGER") ||
                            user.get().getRole().name().equals("ADMIN")
            ) {
                taskCommentService.delete(id);
                return ResponseEntity.ok(id);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this task.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
