package com.example.rosnama.Repository;

import com.example.rosnama.Model.EventRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRequestRepository extends JpaRepository<EventRequest, Integer> {
    EventRequest findEventRequestById(Integer id);
}
