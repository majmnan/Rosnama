package com.example.rosnama.Repository;

import com.example.rosnama.Model.EventOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventOwnerRepository extends JpaRepository<EventOwner, Integer> {

    EventOwner findEventOwnerById(Integer id);

}
