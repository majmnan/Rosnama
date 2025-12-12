//package com.example.rosnama.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//@RequiredArgsConstructor
//public class OpenAIConfig {
//
//    private final Environment env;
//
//    @Bean
//    public WebClient openaiClient() {
//        return WebClient.builder()
//                .baseUrl("https://api.openai.com/v1/")
//                .defaultHeader("Authorization",
//                        "Bearer " + env.getProperty("openai.api.key"))
//                .defaultHeader("Content-Type", "application/json")
//                .build();
//    }
//}
