package com.microrelief.ai_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Component
public class GeminiClient {

    private final RestClient restClient = RestClient.create();

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public String analyze(String title, String description) {
        String prompt = """
            You are a task-categorization assistant for a community help platform.
            Given this task, respond with STRICT JSON only, no extra text, in this exact shape:
            {"category": "one short word", "urgencyScore": number from 1 to 5, "flaggedUnsafe": true or false}

            Title: %s
            Description: %s
            """.formatted(title, description);

        Map<String, Object> requestBody = Map.of(
            "contents", new Object[] {
                Map.of("parts", new Object[] { Map.of("text", prompt) })
            }
        );

        Map<String, Object> response = restClient.post()
        		.uri(apiUrl)
                .header("x-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        // Navigate Gemini's response structure down to the actual text
        var candidates = (java.util.List<Map<String, Object>>) response.get("candidates");
        var content = (Map<String, Object>) candidates.get(0).get("content");
        var parts = (java.util.List<Map<String, Object>>) content.get("parts");
        return (String) parts.get(0).get("text");
    }
}