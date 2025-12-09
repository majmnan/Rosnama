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
    @GetMapping("/get/{adminId}")
    public ResponseEntity<?> getAllExternalEvents(@PathVariable Integer adminId){
        return ResponseEntity.status(HttpStatus.OK).body(externalEventService.getAllExternalEvents(adminId));
    }

    // add
    @PostMapping("/add/{adminId}")
    public ResponseEntity<?> addExternalEvent(@PathVariable Integer adminId, @RequestBody @Valid ExternalEvent externalEvent){
        externalEventService.addExternalEventByAdmin(adminId, externalEvent);
        return ResponseEntity.status(HttpStatus.OK).body("External Event has been added successfully");
    }

    // update
    @PutMapping("/{adminId}/update/{id}")
    public ResponseEntity<ApiResponse> updateExternalEvent(@PathVariable Integer adminId, @PathVariable Integer id, @RequestBody @Valid ExternalEvent externalEvent){
        externalEventService.updateExternalEventByAdmin(adminId, id, externalEvent);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External Event has been updated successfully"));
    }


    //delete
    @DeleteMapping("{adminId}/delete/{id}")
    public ResponseEntity<?> deleteExternalEvent(@PathVariable Integer adminId, @PathVariable Integer id){
        externalEventService.deleteExternalEvent(adminId, id);
        return ResponseEntity.status(HttpStatus.OK).body("External Event has been deleted successfully");
    }
}
