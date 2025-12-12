package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationTestController {
    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<?> send(){
        notificationService.notify(
                "majmnan@gmail.com",
                "966562298998",
                "Test",
                "Mohannad",
                "1-%s2-%s3-%s".formatted("good","man","!")
        );
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("sent successfully"));
    }
}
