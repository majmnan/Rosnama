package com.example.rosnama.Repository;

import com.example.rosnama.Model.ExternalEventRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalEventRequestRepository extends JpaRepository<ExternalEventRequest, Integer> {

    ExternalEventRequest findExternalEventRequestById(Integer id);

}
