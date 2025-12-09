package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Model.EventOwner;
import com.example.rosnama.Service.EventOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event-owner")
public class EventOwnerController {
    private final EventOwnerService eventOwnerService;

    @GetMapping("/get")
    public ResponseEntity<List<EventOwner>> getEventOwners(){
        return ResponseEntity.status(HttpStatus.OK).body(eventOwnerService.getEventOwners());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addEvent(@RequestBody@Valid EventOwner eventOwner){
        eventOwnerService.addEventOwner(eventOwner);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("event added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEvent(@PathVariable Integer id, @RequestBody@Valid EventOwner eventOwner){
        eventOwnerService.updateEventOwner(id, eventOwner);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("event updated successfully"));
    }

    @DeleteMapping("/delete/{id}")

    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable Integer id){
        eventOwnerService.deleteEventOwner(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("event deleted successfully"));
    }
}
