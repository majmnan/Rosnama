package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test-schedule")
public class ScheduledTestingController {
    private final ScheduleService scheduleService;

    @PutMapping("/daily")
    public ResponseEntity<ApiResponse> dailyScheduled(){
        scheduleService.dailyChanges();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("daily scheduled method runes successfully"));
    }
}
