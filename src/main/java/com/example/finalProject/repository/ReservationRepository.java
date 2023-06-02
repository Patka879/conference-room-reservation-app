package com.example.finalProject.repository;

import com.example.finalProject.model.Organization;
import com.example.finalProject.model.Reservation;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation>findByIdentifier(String identifier);

}
