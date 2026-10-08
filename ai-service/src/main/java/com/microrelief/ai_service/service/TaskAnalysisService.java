package com.microrelief.ai_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microrelief.ai_service.dto.TaskCreatedEvent;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Map;

@Service
public class TaskAnalysisService {

    private final GeminiClient geminiClient;
    private final DiscoveryClient discoveryClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TaskAnalysisService(GeminiClient geminiClient, DiscoveryClient discoveryClient) {
        this.geminiClient = geminiClient;
        this.discoveryClient = discoveryClient;
    }

    public void process(TaskCreatedEvent event) {
        try {
            String rawJson = geminiClient.analyze(event.getTitle(), event.getDescription());
            String cleanJson = rawJson.replace("```json", "").replace("```", "").trim();
            Map<String, Object> analysis = objectMapper.readValue(cleanJson, Map.class);

            // Manually ask Eureka for TASK-SERVICE's real address - no @LoadBalanced needed
            List<ServiceInstance> instances = discoveryClient.getInstances("TASK-SERVICE");
            if (instances.isEmpty()) {
                throw new IllegalStateException("No instances of TASK-SERVICE found in Eureka");
            }
            String taskServiceUrl = instances.get(0).getUri().toString();

            RestClient.create().patch()
                    .uri(taskServiceUrl + "/api/tasks/" + event.getTaskId() + "/ai-analysis")
                    .header("Content-Type", "application/json")
                    .body(analysis)
                    .retrieve()
                    .toBodilessEntity();

        } catch (Exception e) {
            System.err.println("AI analysis failed for task " + event.getTaskId() + ": " + e.getMessage());
        }
    }
}