package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.DTO.ReviewDTO;
import com.example.rosnama.Model.Registration;
import com.example.rosnama.Model.Review;
import com.example.rosnama.Repository.RegistrationRepository;
import com.example.rosnama.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    // connect to Database
    private final ReviewRepository reviewRepository;
    private final RegistrationRepository registrationRepository;
    private final NotificationService notificationService;

    // get all reviews
    public List<Review> getAllReviewsByInternalEventId(Integer eventId) {
        return reviewRepository.getReviewsByInternalEventId(eventId);
    }

    // add a review
    public void addReview(ReviewDTO dto) {

        // check if registration exists
        Registration registration = registrationRepository.findRegistrationById(dto.getRegistrationId());
        if (registration == null) {
            throw new ApiException("Registration not found");
        }

        // check if user has used the registration link or registered in the system
        if (!registration.getStatus().equalsIgnoreCase("Used")) {
            throw new ApiException("You can only review after use registration");
        }

        // create review
        Review review = new Review(registration.getId(), dto.getRating(), dto.getDescription(), registration);

        // save
        reviewRepository.save(review);

        //send notification to user that review is submitted successfully
        notificationService.notify(
                registration.getUser().getEmail(),
                registration.getUser().getPhoneNumber(),
                "Review confirmation",
                registration.getUser().getUsername(),
                "Your review has been submitted successfully. Thank you for your feedback!"
        );

    }

    // update review
    public void updateReview(Integer reviewId, ReviewDTO dto) {

        // check if review exists
        Review old = reviewRepository.findReviewById(reviewId);
        if (old == null) {
            throw new ApiException("Review not found");
        }

        // update
        old.setDescription(dto.getDescription());
        old.setRating(dto.getRating());

        // save
        reviewRepository.save(old);
    }

    // delete review
    public void deleteReview(Integer reviewId) {

        // check if review exists
        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null) {
            throw new ApiException("Review not found");
        }

        // delete
        reviewRepository.delete(review);
    }



}
