package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.DTO.InternalEventDTO;
import com.example.rosnama.Model.InternalEvent;
import com.example.rosnama.Service.InternalEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internal-event")
@RequiredArgsConstructor
public class InternalEventController {

    private final InternalEventService internalEventService;

    @GetMapping("/get")
    public ResponseEntity<List <InternalEvent>> getAllInternalEvents(){
        return ResponseEntity.status(HttpStatus.OK).body(internalEventService.getAllAllInternalEvents());
    }

    @PostMapping("/add/{eventOwnerId}")
    public ResponseEntity<ApiResponse> addInternalEvents(@PathVariable Integer eventOwnerId , @RequestBody @Valid InternalEventDTO internalEventDTO){
        internalEventService.addInternalEventByOwner(eventOwnerId , internalEventDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Internal Events Add successfully"));
    }

    @PutMapping("/update/{ownerId}/{eventId}")
    public ResponseEntity<ApiResponse> updateInternalEvents(@PathVariable Integer ownerId , @PathVariable Integer eventId, @RequestBody @Valid InternalEventDTO internalEventDTO ){
        internalEventService.updateInternalEventByOwner(ownerId , eventId , internalEventDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Internal Events update successfully"));
    }

    @DeleteMapping("/delete/{ownerId}/{internalEventId}")
    public ResponseEntity<ApiResponse> deleteInternalEvents(@PathVariable Integer ownerId, @PathVariable Integer internalEventId ){
        internalEventService.deleteInternalEventByOwner(ownerId, internalEventId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Internal Events deleted successfully"));
    }
}
