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
            String errorMessage = "Reservation with the identifier " + reservation.getIdentifier() + "' already exists";
            throw new IllegalArgumentException(errorMessage);
        }

        reservation.setIdentifier(reservationIdentifier);
        reservationRepository.save(reservation);
    }
}

//        public void replaceOrganization(long id, Organization newOrganization) {
//            if(organizationRepository.existsById(id)) {
//                newOrganization.setId(id);
//                organizationRepository.save(newOrganization);
//            }
//            else throw new IllegalArgumentException("Organization doesn't exists with id: " + id);
//        }
//
//        public void addRoomToOrganization(long organizationId, long roomId) {
//            Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
//            Optional<Room> roomOptional = roomRepository.findById(roomId);
//
//            if (organizationOptional.isPresent() && roomOptional.isPresent()) {
//                Organization organization = organizationOptional.get();
//                Room room = roomOptional.get();
//                room.setOrganization(organization);
//                organization.getRooms().add(room);
//                roomRepository.save(room);
//                organizationRepository.save(organization);
//            } else {
//                throw new IllegalArgumentException("Organization or Room not found");
//            }
//        }
//}
