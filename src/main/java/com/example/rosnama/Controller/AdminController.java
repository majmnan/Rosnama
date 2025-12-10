package com.example.rosnama.Controller;


import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Model.Admin;
import com.example.rosnama.Service.AdminService;
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





}
