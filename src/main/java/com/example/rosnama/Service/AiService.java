//package com.example.rosnama.Service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class AiService {
//
//    private final WebClient openaiClient;
//
//    @Value("${openai.model}")
//    private String model;
//
//    public String ask(String prompt) {
//
//        Map<String, Object> message = Map.of(
//                "role", "user",
//                "content", prompt
//        );
//
//        Map<String, Object> body = Map.of(
//                "model", model,
//                "messages", List.of(message)
//        );
//
//        Map response = openaiClient.post()
//                .uri("chat/completions")
//                .bodyValue(body)
//                .retrieve()
//                .bodyToMono(Map.class)
//                .block();
//
//        Map choice = (Map)((List)response.get("choices")).get(0);
//        Map msg = (Map) choice.get("message");
//        return msg.get("content").toString();
//    }
//}
