package com.example.rosnama.Repository;

import com.example.rosnama.DTO.ExternalEventDTO;
import com.example.rosnama.Model.ExternalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExternalEventRepository extends JpaRepository<ExternalEvent, Integer> {

    ExternalEvent findExternalEventById (Integer id);

    List<ExternalEvent> findExternalEventsByStatus (String status);

}
