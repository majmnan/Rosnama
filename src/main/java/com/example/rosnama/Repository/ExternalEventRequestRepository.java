package com.example.rosnama.Repository;

import com.example.rosnama.Model.ExternalEventRequest;
import com.sun.jdi.request.EventRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalEventRequestRepository extends JpaRepository<ExternalEventRequest, Integer> {
    ExternalEventRequest findExternalEventRequestById(Integer id);
}
