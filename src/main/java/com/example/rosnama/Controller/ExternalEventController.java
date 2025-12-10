package com.example.rosnama.Controller;


import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.DTO.ExternalEventDTO;
import com.example.rosnama.Model.ExternalEvent;
import com.example.rosnama.Service.ExternalEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/external-event")
public class ExternalEventController {

    // connect to the DataBase
    private final ExternalEventService externalEventService;


    // get
    @GetMapping("/get/{adminId}")
    public ResponseEntity<List<ExternalEvent>> getAllExternalEvents(@PathVariable Integer adminId){
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.getAllExternalEvents(adminId));
    }

    // add
    @PostMapping("/add/{adminId}")
    public ResponseEntity<ApiResponse> addExternalEvent(@PathVariable Integer adminId, @RequestBody @Valid ExternalEventDTO externalEventDTO){
        externalEventService.addExternalEventByAdmin(adminId, externalEventDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External Event has been added successfully"));
    }

    // update
    @PutMapping("/{adminId}/update/{id}")
    public ResponseEntity<ApiResponse> updateExternalEvent(@PathVariable Integer adminId, @PathVariable Integer id, @RequestBody @Valid ExternalEventDTO externalEventDTO){
        externalEventService.updateExternalEventByAdmin(adminId, id, externalEventDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External Event has been updated successfully"));
    }


    //delete
    @DeleteMapping("{adminId}/delete/{id}")
    public ResponseEntity<ApiResponse> deleteExternalEvent(@PathVariable Integer adminId, @PathVariable Integer id){
        externalEventService.deleteExternalEvent(adminId, id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External Event has been deleted successfully"));
    }

    // add external event by owner
    @PostMapping("/owner-add")
    public ResponseEntity<ApiResponse> addEventByOwner(@Valid @RequestBody ExternalEventDTO dto){
        externalEventService.requestEventByOwner(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External event created successfully and request generated automatically"));
    }


    // update external event by owner
    @PutMapping("/owner/{ownerId}/update/{eventId}")
    public ResponseEntity<ApiResponse> updateEventByOwner(@PathVariable Integer ownerId, @PathVariable Integer eventId, @Valid @RequestBody ExternalEventDTO dto){
        externalEventService.updateEventByOwner(ownerId, eventId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External event updated successfully"));
    }

    // delete external event by owner
    @DeleteMapping("/owner/{ownerId}/delete/{eventId}")
    public ResponseEntity<ApiResponse> deleteEventByOwner(@PathVariable Integer ownerId, @PathVariable Integer eventId){
        externalEventService.deleteEventByOwner(ownerId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External event deleted successfully"));
    }
}
