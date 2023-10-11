package com.plancpu.backend.repository;

import com.plancpu.backend.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCommentRepository  extends JpaRepository<TaskComment, Long> {
}
