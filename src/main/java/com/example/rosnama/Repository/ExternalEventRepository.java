package com.example.rosnama.Repository;

import com.example.rosnama.DTO.ExternalEventDTO;
import com.example.rosnama.Model.ExternalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExternalEventRepository extends JpaRepository<ExternalEvent, Integer> {

    ExternalEvent findExternalEventById (Integer id);

    List<ExternalEvent> findExternalEventsByStatus(String status);

    List<ExternalEvent> findExternalEventsByType(String type);

    List<ExternalEvent> findExternalEventsByCity(String city);

    List<ExternalEvent> findExternalEventsByStartDate(LocalDate startDate);

    List<ExternalEvent> findExternalEventsByEndDate(LocalDate endDate);
}
