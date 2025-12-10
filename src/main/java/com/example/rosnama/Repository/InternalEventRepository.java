package com.example.rosnama.Repository;

import com.example.rosnama.Model.EventOwner;
import com.example.rosnama.Model.ExternalEvent;
import com.example.rosnama.Model.InternalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternalEventRepository extends JpaRepository<InternalEvent, Integer> {
    InternalEvent findInternalEventById(Integer id);

}
