package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.DTO.InternalEventDTOIn;
import com.example.rosnama.DTO.InternalEventDTOOut;
import com.example.rosnama.Model.InternalEvent;
import com.example.rosnama.Model.Registration;
import com.example.rosnama.Service.InternalEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/internal-event")
@RequiredArgsConstructor
public class InternalEventController {

    private final InternalEventService internalEventService;

    @GetMapping("/get")
    public ResponseEntity<List<InternalEvent>> getAllInternalEvents(){
        return ResponseEntity.status(HttpStatus.OK).body(internalEventService.getAllAllInternalEvents());
    }

    @PostMapping("/add/{eventOwnerId}/{adminId}")
    public ResponseEntity<ApiResponse> addInternalEvents(@PathVariable Integer eventOwnerId , @PathVariable Integer adminId ,  @RequestBody @Valid InternalEventDTOIn DTO){
        internalEventService.addInternalEventByOwner(eventOwnerId , DTO ,adminId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Internal Events Add successfully"));
    }

    @PutMapping("/update/{ownerId}/{eventId}")
    public ResponseEntity<ApiResponse> updateInternalEvents(@PathVariable Integer ownerId , @PathVariable Integer eventId, @RequestBody @Valid InternalEventDTOIn DTO){
        internalEventService.updateInternalEventByOwner(ownerId , eventId , DTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Internal Events update successfully"));
    }

    @DeleteMapping("/delete/{ownerId}/{internalEventId}")
    public ResponseEntity<ApiResponse> deleteInternalEvents(@PathVariable Integer ownerId, @PathVariable Integer internalEventId ){
        internalEventService.deleteInternalEventByOwner(ownerId, internalEventId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Internal Events deleted successfully"));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<InternalEventDTOOut>>getInternalEventsByType(@PathVariable String type){
       return ResponseEntity.status(HttpStatus.OK).body(internalEventService.getInternalEventsByType(type));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<InternalEventDTOOut>>getInternalEventsByCity(@PathVariable String city){
        return ResponseEntity.status(HttpStatus.OK).body(internalEventService.getInternalEventsByCity(city));
    }

    @GetMapping("/between/{after}/{before}")
    public ResponseEntity<List<InternalEventDTOOut>>getInternalEventByEndDateBetween(@PathVariable LocalDate after,@PathVariable LocalDate before){
        return ResponseEntity.status(HttpStatus.OK).body(internalEventService.getInternalEventByDateBetween(after, before));
    }

    @GetMapping("/get-category/{categoryId}")
    public ResponseEntity<List<InternalEventDTOOut>> getOngoingByCategory(@PathVariable Integer categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(internalEventService.getOngoingByCategory(categoryId));
    }

    @GetMapping("/recommend-user/{userId}")
    public ResponseEntity<List<InternalEventDTOOut>> recommendByUserAttendedEvent(@PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(internalEventService.recommendDependsOnUserAttendedEvents(userId));
    }


}
