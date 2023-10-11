package com.plancpu.backend.service;

import com.plancpu.backend.entity.Task;
import com.plancpu.backend.entity.TaskComment;
import com.plancpu.backend.repository.TaskCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;

    public Optional<TaskComment> getTaskCommentById(Long id) {
        Optional<TaskComment> taskComment = taskCommentRepository.findById(id);
        if (taskComment.isPresent()) {
            return taskCommentRepository.findById(id);
        } else {
            return null;
        }
    }

    public List<TaskComment> getAllTaskCommentsByTaskId(Long taskId) {
        List<TaskComment> taskComments = taskCommentRepository.findAll();
        if (taskComments.size() > 0) {
            List<TaskComment> filteredTasks = taskCommentRepository.findAll()
                    .stream()
                    .filter(comment -> comment.getTaskId() == taskId)
                    .collect(Collectors.toList());
            return filteredTasks;
        } else {
            return taskCommentRepository.findAll();
        }
    }

    public TaskComment createTaskComment(TaskComment taskComment) {
        return taskCommentRepository.save(taskComment);
    }

    public Long delete(Long id) {
        Optional<TaskComment> comment = taskCommentRepository.findById(id);
        if (comment.isPresent()) {
            taskCommentRepository.deleteById(id);
            return id;
        } else {
            return null;
        }
    }
}
