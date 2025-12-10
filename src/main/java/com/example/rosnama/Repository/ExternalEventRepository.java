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

    List<ExternalEvent> findExternalEventsByStartDate(LocalDate date);

    List<ExternalEvent> findExternalEventsByStartDateBetween(LocalDate start, LocalDate end);

    List<ExternalEvent> findExternalEventsByType(String type);

    List<ExternalEvent> findExternalEventsByCity(String city);

    List<ExternalEvent> findExternalEventsByCategory_Id(Integer categoryId);

    @Query("select e from ExternalEvent e join e.users u where u.id = ?1")
    List<ExternalEvent> findExternalEventsByUserAttendance(Integer userId);


}
