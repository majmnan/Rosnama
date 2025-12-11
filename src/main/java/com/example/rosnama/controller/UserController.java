package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Model.User;
import com.example.rosnama.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<List<User> >getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse>addUser(@RequestBody @Valid User user ) {
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User Added successfully"));

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse>updateUser(@PathVariable Integer id ,@RequestBody @Valid User user){
        userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse>deleteUser(@PathVariable Integer id ){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User Deleted Successfully"));
    }
//
//    ///  extra endpoint
//
//    @GetMapping("/recommend/{userId}")
//    public ResponseEntity<ApiResponse> generateAiRecommendations(@PathVariable Integer userId) {
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(userService.generateAiRecommendations(userId)));
//    }


}