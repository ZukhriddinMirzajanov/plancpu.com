package com.plancpu.backend.service;

import com.plancpu.backend.entity.Task;
import com.plancpu.backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasksByCompanyId(Long companyId) {
        return taskRepository.findByCompanyId(companyId);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Long delete(Long id) {
        taskRepository.deleteById(id);
        return id;
    }
}
