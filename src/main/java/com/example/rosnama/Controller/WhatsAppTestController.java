package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Service.WhatsAppTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/whatsapp")
@RequiredArgsConstructor
public class WhatsAppTestController {

    private final WhatsAppTemplateService whatsAppTemplateService;


    @PostMapping("/send-template")
    public ResponseEntity<ApiResponse> sendTemplate() {

        whatsAppTemplateService.sendEventTemplate(
                "+966545774754",
                "test",
                "12/1",
                "3pm"
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse("WhatsApp template sent successfully"));
    }


}
