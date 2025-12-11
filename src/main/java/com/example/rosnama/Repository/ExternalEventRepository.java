package com.example.rosnama.Repository;

import com.example.rosnama.DTO.ExternalEventDTO;
import com.example.rosnama.Model.Category;
import com.example.rosnama.Model.ExternalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExternalEventRepository extends JpaRepository<ExternalEvent, Integer> {

    ExternalEvent findExternalEventById (Integer id);

    List<ExternalEvent> findExternalEventsByStatusOrderByEndDateAsc(String status);

    List<ExternalEvent> findExternalEventsByStatusOrderByStartDateAsc(String status);

    List<ExternalEvent> findExternalEventsByType(String type);

    List<ExternalEvent> findExternalEventsByCity(String city);

    List<ExternalEvent> findExternalEventsByStartDate(LocalDate startDate);

    List<ExternalEvent> findExternalEventsByEndDate(LocalDate endDate);

    @Query("select e from ExternalEvent e where e.startDate<?1 and e.endDate>?1 or e.startDate > ?1 and e.startDate <?2 order by e.endDate asc")
    List<ExternalEvent> findExternalEventsByDateBetween(LocalDate after, LocalDate before);

    List<ExternalEvent> findExternalEventsByStatusAndCategoryOrderByEndDateAsc(String status, Category category);
}
