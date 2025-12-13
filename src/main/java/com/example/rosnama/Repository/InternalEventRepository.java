package com.example.rosnama.Repository;


import com.example.rosnama.Model.Category;
import com.example.rosnama.Model.ExternalEvent;
import com.example.rosnama.Model.InternalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InternalEventRepository extends JpaRepository<InternalEvent, Integer> {

    InternalEvent findInternalEventById(Integer id);

    List<InternalEvent> findInternalEventsByType(String type);

    List<InternalEvent> findInternalEventsByCity(String city);

    List<InternalEvent> findInternalEventByStartDate(LocalDate startDate);

    List<InternalEvent> findInternalEventByEndDate(LocalDate endDate);

    @Query("select e from InternalEvent e where e.startDate<?1 and e.endDate>?1 or e.startDate > ?1 and e.startDate <?2 order by e.endDate asc")
    List<InternalEvent> findInternalEventsByDateBetween(LocalDate after, LocalDate before);

    List<InternalEvent> findInternalEventsByStatusAndCategoryOrderByEndDateAsc(String status, Category category);

    @Query("select distinct e from InternalEvent e join e.registrations r where r.user.id = ?1 and r.status=?2")
    List<InternalEvent> findInternalEventsByUserIdAndRegistrationStatus(Integer userId, String status);
}