package com.example.rosnama.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class OpenAIConfig {

    @Bean
    public WebClient openaiClient() {
        return WebClient.builder()
            .baseUrl("https://api.openai.com")
            .build();
    }

}
