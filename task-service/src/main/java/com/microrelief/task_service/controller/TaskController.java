package com.microrelief.task_service.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.microrelief.task_service.entity.Task;
import com.microrelief.task_service.service.TaskService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
    
    @PatchMapping("/{id}/ai-analysis")
    public Task updateAiAnalysis(@PathVariable Long id, @RequestBody Map<String, Object> analysis) {
        return taskService.updateAiAnalysis(id,
                (String) analysis.get("category"),
                (Integer) analysis.get("urgencyScore"),
                (Boolean) analysis.get("flaggedUnsafe"));
    }
}
