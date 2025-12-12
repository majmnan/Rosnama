package com.example.rosnama.Repository;

import com.example.rosnama.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewById(Integer id);

    @Query("select rev from Review rev where rev.registration.id in (select reg.id from Registration reg where reg.internalEvent.id = ?1)")
    List<Review> getReviewsByInternalEventId(Integer internalEventId);
}
