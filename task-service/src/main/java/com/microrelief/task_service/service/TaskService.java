package com.microrelief.task_service.service;

import org.springframework.stereotype.Service;
import com.microrelief.task_service.entity.Task;
import com.microrelief.task_service.entity.TaskCreatedEvent;
import com.microrelief.task_service.repository.TaskRepository;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
    private final KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate;

    public TaskService(TaskRepository taskRepository, KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate) {
        this.taskRepository = taskRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Task createTask(Task task) {
        Task saved = taskRepository.save(task);

        TaskCreatedEvent event = new TaskCreatedEvent(saved.getId(), saved.getTitle(), saved.getDescription());
        kafkaTemplate.send("task-created-topic", event);

        return saved;
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = getTask(id);
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    
    public Task updateAiAnalysis(Long id, String category, Integer urgencyScore, Boolean flaggedUnsafe) {
        Task task = getTask(id);
        task.setAiCategory(category);
        task.setUrgencyScore(urgencyScore);
        task.setFlaggedUnsafe(flaggedUnsafe);
        return taskRepository.save(task);
    }
}
