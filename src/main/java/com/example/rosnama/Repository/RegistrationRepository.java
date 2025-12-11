package com.example.rosnama.Repository;

import com.example.rosnama.Model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    Registration findRegistrationById(Integer id);

    List<Registration> findRegistrationsByDate(LocalDate date);

    List<Registration> findRegistrationsByDateAndStatus(LocalDate date, String status);
}
