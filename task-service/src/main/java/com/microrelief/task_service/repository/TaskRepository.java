package com.microrelief.task_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microrelief.task_service.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
