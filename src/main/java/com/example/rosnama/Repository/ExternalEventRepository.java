package com.example.rosnama.Repository;

import com.example.rosnama.Model.ExternalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalEventRepository extends JpaRepository<ExternalEvent, Integer> {

    ExternalEvent findExternalEventById (Integer id);

}
