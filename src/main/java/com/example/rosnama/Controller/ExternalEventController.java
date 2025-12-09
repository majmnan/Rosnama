package com.example.rosnama.Controller;


import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Model.ExternalEvent;
import com.example.rosnama.Service.ExternalEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/external-event")
public class ExternalEventController {

    // connect to the DataBase
    private final ExternalEventService externalEventService;


    // get
    @GetMapping("/get")
    public ResponseEntity<?> getAllExternalEvents(){
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.getAllExternalEvents());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addExternalEvent(@RequestBody @Valid ExternalEvent externalEvent){
        return ResponseEntity.status(HttpStatus.OK).body("External Event has been added successfully");
    }

    // update
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateExternalEvent(@PathVariable Integer id, @RequestBody @Valid ExternalEvent externalEvent){
        externalEventService.updateExternalEvent(id,externalEvent);
        return ResponseEntity.status(HttpStatus.OK).body("External Event has been updated successfully");
    }


    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExternalEvent( @PathVariable Integer id){
        externalEventService.deleteExternalEvent(id);
        return ResponseEntity.status(HttpStatus.OK).body("External Event has been deleted successfully");
    }


    ///  extera endpoints


    // add external event by admin
    @PostMapping("/admin-add-event/{adminId}")
    public ResponseEntity<?> addExternalEventByAdmin(@PathVariable Integer adminId, @RequestBody @Valid ExternalEvent externalEvent) {

        externalEventService.addExternalEventByAdmin(adminId, externalEvent);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("external event created successfully"));
    }

    // admin publesh the event to user
    @PutMapping("/admin-publish-event/{adminId}/publish/{eventId}")
    public ResponseEntity<?> publishExternalEventByAdmin(@PathVariable Integer adminId, @PathVariable Integer eventId) {

        externalEventService.publishExternalEvent(adminId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("external event published successfully"));

    }
}
