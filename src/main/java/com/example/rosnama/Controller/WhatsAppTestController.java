package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Service.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/whatsapp")
@RequiredArgsConstructor
public class WhatsAppTestController {

    private final WhatsAppService whatsAppService;


    @PostMapping("/send/{phone}/{msg}")
    public ResponseEntity<ApiResponse>send(@PathVariable String phone , @PathVariable String msg) {


               whatsAppService.whatsapp(phone, msg);

        return ResponseEntity
                .status(HttpStatus.OK).body(new ApiResponse("WhatsApp template sent successfully"));
    }



}
