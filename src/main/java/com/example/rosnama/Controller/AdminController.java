package com.example.rosnama.Controller;


import com.example.rosnama.Model.Admin;
import com.example.rosnama.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    // connect to the DataBase
    private final AdminService adminService;


    // get
    @GetMapping("/get")
    public ResponseEntity<?> getAllAdmins(){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmins());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@Valid @RequestBody Admin admin){
        adminService.addAdmin(admin);
        return ResponseEntity.status(HttpStatus.OK).body("Admin information has been added successfully");
    }



    // update
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @Valid @RequestBody Admin admin){
        adminService.updateAdmin(id,admin);
        return ResponseEntity.status(HttpStatus.OK).body("Admin information has been updated successfully");
    }


    // delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id){
        adminService.deleteAdmin(id);
        return ResponseEntity.status(HttpStatus.OK).body("Admin information has been deleted successfully");
    }
}
