package com.plancpu.backend.service;

import com.plancpu.backend.entity.Task;
import com.plancpu.backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasksByCompanyProjectId(Long companyProjectId) {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.size() > 0) {
            List<Task> filteredTasks = taskRepository.findAll()
                    .stream()
                    .filter(task -> task.getCompanyProject().getId() == companyProjectId)
                    .collect(Collectors.toList());
            return filteredTasks;
        } else {
            return taskRepository.findAll();
        }
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Long delete(Long id) {
        taskRepository.deleteById(id);
        return id;
    }
}
