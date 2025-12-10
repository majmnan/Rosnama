package com.example.rosnama.Repository;


import com.example.rosnama.Model.InternalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InternalEventRepository extends JpaRepository<InternalEvent, Integer> {

    InternalEvent findInternalEventById(Integer id);

    List<InternalEvent> findInternalEventsByStartDate(LocalDate date);

    List<InternalEvent> findInternalEventsByStartDateBetween(LocalDate start, LocalDate end);

    List<InternalEvent> findInternalEventsByType(String type);

    List<InternalEvent> findInternalEventsByCity(String city);

    List<InternalEvent> findInternalEventsByCategory_Id(Integer categoryId);


}
