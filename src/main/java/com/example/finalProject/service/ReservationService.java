package com.example.finalProject.service;

import com.example.finalProject.model.Organization;
import com.example.finalProject.model.Reservation;
import com.example.finalProject.model.Room;
import com.example.finalProject.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;


    public List<Reservation> listReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(long id) {
        return reservationRepository.findById(id);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getReservationByIdentifier(String identifier) {
        return reservationRepository.findByIdentifier(identifier);
    }

    public void addReservation(Reservation reservation) {
        String reservationIdentifier = reservation.getIdentifier().toLowerCase();
        List<Reservation> existingReservations = getReservationByIdentifier(reservationIdentifier);

        if (!existingReservations.isEmpty()) {
            String errorMessage = "Reservation with the identifier " + reservation.getIdentifier() + " already exists";
            throw new IllegalArgumentException(errorMessage);
        }

        reservation.setIdentifier(reservationIdentifier);
        reservationRepository.save(reservation);
    }


    public void replaceReservation(long id, Reservation newReservation) {
        if (reservationRepository.existsById(id)) {
            newReservation.setId(id);
            reservationRepository.save(newReservation);
        } else throw new IllegalArgumentException("Reservation doesn't exists with id: " + id);
    }
}

