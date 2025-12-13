package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Model.ExternalEventRequest;
import com.example.rosnama.Service.ExternalEventRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/external-event-request")
public class ExternalEventRequestController {

    private final ExternalEventRequestService externalEventRequestService;

    @GetMapping("/get/{adminId}")
    public ResponseEntity<List<ExternalEventRequest>> getExternalEventRequests(@PathVariable Integer adminId){
        return ResponseEntity.status(HttpStatus.OK).body(externalEventRequestService.getExternalEventRequests(adminId));
    }

    @PutMapping("/{adminId}/offer-for/{requestId}/{price}")
    public ResponseEntity<ApiResponse> offerPrice(@PathVariable Integer adminId, @PathVariable Integer requestId, @PathVariable Double price){
        externalEventRequestService.offerPrice(adminId, requestId, price);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("offer send successfully"));
    }

    @PutMapping("/negotiates/{ownerId}/{requestId}/{price}/{adminId}")
    public ResponseEntity<ApiResponse> negotiates(@PathVariable Integer ownerId, @PathVariable Integer requestId, @PathVariable Integer adminId,  @PathVariable Double price){
        externalEventRequestService.negotiates(ownerId, requestId, price ,adminId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("negotiate successfully"));
    }

    @PutMapping("/accept-pay/{ownerId}/{requestId}/{adminId}")
    public ResponseEntity<ApiResponse> acceptAndPay(@PathVariable Integer ownerId, @PathVariable Integer requestId , @PathVariable Integer adminId){
        externalEventRequestService.acceptOfferAndPay(ownerId, requestId , adminId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("event accepted and published successfully successfully"));

    }


}
