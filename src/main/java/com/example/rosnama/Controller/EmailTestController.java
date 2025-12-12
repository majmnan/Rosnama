package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailTestController {

    private final EmailService emailService;

    // Test email endpoint
    @PostMapping("/test")
    public ResponseEntity<ApiResponse> sendTestEmail() {

        emailService.sendEmail(
                "ibra9v1221@gmail.com",
                "Test Email from Rosnama",
                """
                Hello Ibrahim 

                This is a test email sent successfully from the Rosnama system.

                If you received this email, your email configuration is working perfectly 

                Regards,
                Rosnama Team
                """
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse("Test email sent successfully"));
    }
}
