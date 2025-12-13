package com.example.rosnama.Controller;


import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.DTO.ExternalEventDTOIn;
import com.example.rosnama.DTO.ExternalEventDTOOut;
import com.example.rosnama.DTO.InternalEventDTOOut;
import com.example.rosnama.Model.ExternalEvent;
import com.example.rosnama.Service.ExternalEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<ApiResponse> addExternalEvent(@PathVariable Integer adminId, @RequestBody @Valid ExternalEventDTOIn externalEventDTO){
        externalEventService.addExternalEventByAdmin(adminId, externalEventDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External Event has been added successfully"));
    }

    // update
    @PutMapping("/{adminId}/update/{id}")
    public ResponseEntity<ApiResponse> updateExternalEvent(@PathVariable Integer adminId, @PathVariable Integer id, @RequestBody @Valid ExternalEventDTOIn externalEventDTO){
        externalEventService.updateExternalEventByAdmin(adminId, id, externalEventDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External Event has been updated successfully"));
    }


    //delete
    @DeleteMapping("/{adminId}/delete/{id}")
    public ResponseEntity<ApiResponse> deleteExternalEvent(@PathVariable Integer adminId, @PathVariable Integer id){
        externalEventService.deleteExternalEvent(adminId, id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External Event has been deleted successfully"));
    }

    ///  extra end points

    // add external event by owner
    @PostMapping("/request-event")
    public ResponseEntity<ApiResponse> addEventByOwner(@Valid @RequestBody ExternalEventDTOIn dto){
        externalEventService.requestEventByOwner(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External event created successfully and request generated automatically"));
    }


    // update external event by owner
    @PutMapping("/owner/{ownerId}/update/{eventId}")
    public ResponseEntity<ApiResponse> updateEventByOwner(@PathVariable Integer ownerId, @PathVariable Integer eventId, @Valid @RequestBody ExternalEventDTOIn dto){
        externalEventService.updateEventByOwner(ownerId, eventId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External event updated successfully"));
    }

    // delete external event by owner
    @DeleteMapping("/owner/{ownerId}/delete/{eventId}")
    public ResponseEntity<ApiResponse> deleteEventByOwner(@PathVariable Integer ownerId, @PathVariable Integer eventId){
        externalEventService.deleteEventByOwner(ownerId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External event deleted successfully"));
    }

    // get all active events to show to users
    @GetMapping("/upcoming")
    public ResponseEntity<List<ExternalEventDTOOut>> getUpcomingExternalEvents(){
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.getUpcomingExternalEvents());
    }

    @GetMapping("/ongoing")
    public ResponseEntity<List<ExternalEventDTOOut>> getOnGoingEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.getOnGoingExternalEvents());
    }

    @GetMapping("/between/{after}/{before}")
    public ResponseEntity<List<ExternalEventDTOOut>> getEventsOnGoingBetween(@PathVariable LocalDate after, @PathVariable LocalDate before) {
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.getEventsOnGoingBetween(after, before));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ExternalEventDTOOut>> getEventsByType(@PathVariable String type) {
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.getEventsByType(type));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<ExternalEventDTOOut>> getEventsByCity(@PathVariable String city) {
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.getEventsByCity(city));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ExternalEventDTOOut>> getOngoingByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.getOngoingByCategory(categoryId));
    }

    @GetMapping("recommend-event/{eventId}")
    public ResponseEntity<List<ExternalEventDTOOut>> recommendByEvent(@PathVariable Integer eventId){
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.recommendDependsOnEvent(eventId));
    }

    @GetMapping("/recommend-user-attending/{userId}")
    public ResponseEntity<List<ExternalEventDTOOut>>recommendDependsOnUserAttendedEvents(@PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.recommendDependsOnUserAttendedEvents(userId));
    }

    @GetMapping("recommend-user-likes/{userId}")
    public ResponseEntity<List<ExternalEventDTOOut>> recommendByUserHighRateEvents(@PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.recommendDependsOnUserHighRateEvents(userId));
    }
}
