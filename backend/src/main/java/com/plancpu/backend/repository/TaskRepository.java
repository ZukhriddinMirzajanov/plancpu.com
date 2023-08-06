package com.plancpu.backend.repository;

import com.plancpu.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompanyId(Long id);
}
