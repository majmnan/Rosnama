package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.DTO.InternalEventDTOIn;
import com.example.rosnama.Model.InternalEvent;
import com.example.rosnama.Service.InternalEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internalEvent")
@RequiredArgsConstructor
public class InternalEventController {


    private final InternalEventService internalEventService;


    @GetMapping("/get")
    public ResponseEntity<List <InternalEvent>> getAllInternalEvents(){
        return ResponseEntity.status(HttpStatus.OK).body(internalEventService.getAllAllInternalEvents());
    }

@PostMapping("/add/{event_owner_id}")
    public ResponseEntity<ApiResponse> addInternalEvents(@PathVariable Integer event_owner_id , @RequestBody @Valid InternalEventDTOIn internalEventDTOIn){
        internalEventService.addInternalEventByOwner(event_owner_id , internalEventDTOIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Internal Events Add successfully"));
    }

@PutMapping("/update/{owner_id}/{event_id}")
    public ResponseEntity<ApiResponse> updateInternalEvents(@PathVariable Integer owner_id , @PathVariable Integer event_id, @RequestBody @Valid InternalEventDTOIn internalEventDTOIn ){
        internalEventService.updateInternalEventByOwner(owner_id , event_id , internalEventDTOIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Internal Events update successfully"));
    }

@DeleteMapping("/delete/{owner_id}")
    public ResponseEntity<ApiResponse> deleteInternalEvents(@PathVariable Integer owner_id ){
        internalEventService.deleteInternalEventByOwner(owner_id );
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Internal Events deleted successfully"));
    }
}
