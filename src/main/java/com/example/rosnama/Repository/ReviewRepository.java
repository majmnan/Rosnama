package com.example.rosnama.Repository;

import com.example.rosnama.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewById(Integer id);

    Review findReviewByRegistrationId(Integer registrationId);

}
