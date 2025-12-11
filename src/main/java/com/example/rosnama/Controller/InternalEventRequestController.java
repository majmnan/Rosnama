package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Model.InternalEventRequest;
import com.example.rosnama.Service.InternalEventRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internal-event-request")
@RequiredArgsConstructor
public class InternalEventRequestController {

    private final InternalEventRequestService internalEventRequestService;

    @GetMapping("/get/{adminId}")
    public ResponseEntity<List <InternalEventRequest>>getInternalEventRequests(@PathVariable Integer adminId){
        return ResponseEntity.status(HttpStatus.OK).body(internalEventRequestService.getAllEventsRequest(adminId));

    }


    @PutMapping ("/offer-for/{adminId}/{requestId}/{price}")
    public ResponseEntity<ApiResponse>offerPrice(@PathVariable Integer adminId, @PathVariable Integer requestId,@PathVariable Double price){
        internalEventRequestService.offerPrice(adminId,requestId,price);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("offer send successfully"));

    }


    @PutMapping ("/negotiates/{ownerId}/{requestId}/{price}")
    public ResponseEntity<ApiResponse>negotiates(@PathVariable Integer ownerId, @PathVariable Integer requestId, @PathVariable Double price){
        internalEventRequestService.negotiates(ownerId,requestId,price);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("negotiate successfully"));

    }


    @PutMapping ("/accept-pay/{ownerId}/{requestId}/{price}")
    public ResponseEntity<ApiResponse>acceptOfferAndPay(@PathVariable Integer ownerId, @PathVariable Integer requestId){
        internalEventRequestService.acceptOfferAndPay(ownerId,requestId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("event accepted and published successfully "));

    }




}
