package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.DTO.RegistrationDTOIn;
import com.example.rosnama.Service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRegistration(@RequestBody RegistrationDTOIn registrationDTOIn) {
        registrationService.addRegistration(registrationDTOIn);
        return ResponseEntity.ok(new ApiResponse("registration added"));
    }

    @PutMapping("/use/{registrationId}")
    public ResponseEntity<ApiResponse> useRegistration(@PathVariable Integer registrationId) {
        registrationService.useRegistration(registrationId);
        return ResponseEntity.ok(new ApiResponse("registration used"));
    }

    @PutMapping("/use/{registrationId}")
    public ResponseEntity<ApiResponse> findRegistrationByInternalEventAndDate(@PathVariable Integer registrationId) {
        registrationService.useRegistration(registrationId);
        return ResponseEntity.ok(new ApiResponse("registration used"));
    }
}