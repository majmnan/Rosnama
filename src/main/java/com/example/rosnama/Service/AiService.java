package com.example.rosnama.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    private final WebClient openaiClient;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.temperature}")
    private Double temperature;

    public String callAi(String prompt) {

        Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", temperature
        );

        return openaiClient.post()
                .uri("/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response ->
                        ((Map) ((Map) ((List) response.get("choices"))
                                .get(0)).get("message")).get("content").toString()
                )
                .block();
    }

    public String toJson(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JSON serialization failed", e);
        }
    }

    String extractJsonArray(String text) {

        text = text.replace("```json", "").replace("```", "").trim();

        int start = text.indexOf('[');
        int end = text.lastIndexOf(']');

        if (start == -1 || end == -1 || end <= start) {
            throw new RuntimeException("AI response does not contain a JSON array");
        }

        return text.substring(start, end + 1);
    }
}
