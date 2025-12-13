package com.example.rosnama.Repository;

import com.example.rosnama.Model.InternalEventRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalEventRequestRepository extends JpaRepository<InternalEventRequest, Integer> {

   InternalEventRequest findInternalEventRequestById(Integer id);

}
