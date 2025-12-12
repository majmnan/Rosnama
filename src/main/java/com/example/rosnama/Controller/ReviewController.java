package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.DTO.ReviewDTO;
import com.example.rosnama.Model.Review;
import com.example.rosnama.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //get
    @GetMapping("/get/{eventId}")
    public ResponseEntity<List<Review>> getAllReviewsOfInternalEvent(@PathVariable Integer eventId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getAllReviewsByInternalEventId(eventId));
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addReview(@RequestBody @Valid ReviewDTO dto) {
        reviewService.addReview(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Review added successfully"));
    }

    //update
    @PutMapping("/update/{reviewId}")
    public ResponseEntity<ApiResponse> updateReview(@PathVariable Integer reviewId, @RequestBody @Valid ReviewDTO dto) {
        reviewService.updateReview(reviewId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Review updated successfully"));
    }

    //delete
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Review deleted successfully"));
    }

    ///  extra endpoint





}
