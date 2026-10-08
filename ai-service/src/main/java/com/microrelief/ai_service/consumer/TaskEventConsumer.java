package com.microrelief.ai_service.consumer;

import com.microrelief.ai_service.dto.TaskCreatedEvent;
import com.microrelief.ai_service.service.TaskAnalysisService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TaskEventConsumer {

    private final TaskAnalysisService taskAnalysisService;

    public TaskEventConsumer(TaskAnalysisService taskAnalysisService) {
        this.taskAnalysisService = taskAnalysisService;
    }

    @KafkaListener(topics = "task-created-topic", groupId = "ai-service-group")
    public void consume(TaskCreatedEvent event) {
        System.out.println("Received task-created event for task " + event.getTaskId());
        taskAnalysisService.process(event);
    }
}