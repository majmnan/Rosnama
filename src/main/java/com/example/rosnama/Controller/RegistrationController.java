package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.DTO.RegistrationDTOIn;
import com.example.rosnama.Model.Registration;
import com.example.rosnama.Service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRegistration(@RequestBody RegistrationDTOIn registrationDTOIn) {
        registrationService.addRegistration(registrationDTOIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("registered successfully"));
    }

    @PutMapping("/use/{registrationId}")
    public ResponseEntity<ApiResponse> useRegistration(@PathVariable Integer registrationId) {
        registrationService.useRegistration(registrationId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("registration used"));
    }

    @PutMapping("/get-by-event-date/{internalEventId}/{date}")
    public ResponseEntity<List<Registration>> getRegistrationOfEventInADay(@PathVariable Integer internalEventId, @PathVariable LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.getRegistrationOfEventInADay(internalEventId, date));
    }
}