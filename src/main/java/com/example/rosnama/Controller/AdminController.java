package com.example.rosnama.Controller;


import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.DTO.ExternalEventDTOIn;
import com.example.rosnama.Model.Admin;
import com.example.rosnama.Service.AdminService;
import com.example.rosnama.Service.ExternalEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    // connect to the DataBase
    private final AdminService adminService;
    private final ExternalEventService externalEventService;


    // get
    @GetMapping("/get")
    public ResponseEntity<List<Admin >> getAllAdmins(){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmins());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAdmin(@Valid @RequestBody Admin admin){
        adminService.addAdmin(admin);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Admin information has been added successfully") );
    }



    // update
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAdmin(@PathVariable Integer id, @Valid @RequestBody Admin admin){
        adminService.updateAdmin(id,admin);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Admin information has been updated successfully"));
    }


    // delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAdmin(@PathVariable Integer id){
        adminService.deleteAdmin(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Admin information has been deleted successfully"));
    }


    ///  extra endpoints

    // add external event by owner
    @PostMapping("/owner/add")
    public ResponseEntity<ApiResponse> addEventByOwner(@Valid @RequestBody ExternalEventDTOIn dto){
        externalEventService.addEventByOwner(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External event created successfully and request generated automatically"));
    }


    // update external event by owner
    @PutMapping("/owner/update/{ownerId}/{eventId}")
    public ResponseEntity<ApiResponse> updateEventByOwner(@PathVariable Integer ownerId, @PathVariable Integer eventId, @Valid @RequestBody ExternalEventDTOIn dto){
        externalEventService.updateEventByOwner(ownerId, eventId, dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("External event updated successfully"));
    }

    // delete external event by owner
    @DeleteMapping("/owner/delete/{ownerId}/{eventId}")
    public ResponseEntity<ApiResponse> deleteEventByOwner(@PathVariable Integer ownerId, @PathVariable Integer eventId){
        externalEventService.deleteEventByOwner(ownerId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("External event deleted successfully"));
    }



}
