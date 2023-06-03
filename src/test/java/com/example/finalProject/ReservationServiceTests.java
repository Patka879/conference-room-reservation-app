package com.example.finalProject;

import com.example.finalProject.model.Organization;
import com.example.finalProject.model.Reservation;
import com.example.finalProject.model.Room;
import com.example.finalProject.repository.ReservationRepository;
import com.example.finalProject.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTests {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listReservationsShouldReturnAllReservations() {
        // Given
        Reservation res1 = new Reservation("Res1", null, null, null, null, null);
        Reservation res2 = new Reservation("Res2", null, null, null, null, null);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);

        when(reservationRepository.findAll()).thenReturn(reservations);

        // When
        List<Reservation> result = reservationService.listReservations();

        // Then
        assertEquals(reservations, result);
    }

    @Test
    void getReservationByIdShouldReturnReservation() {
        // Given
        long id = 1;
        Reservation res = new Reservation("Res1", null, null, null, null, null);
        res.setId(id);

        when(reservationRepository.findById(id)).thenReturn(Optional.of(res));

        // When
        Optional<Reservation> result = reservationService.getReservationById(id);

        // Then
        assertEquals(Optional.of(res), result);
    }

    @Test
    void getReservationByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        // Given
        long id = 1;

        when(reservationRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Reservation> result = reservationService.getReservationById(id);

        // Then
        assertEquals(Optional.empty(), result);
    }

    @Test
    void addReservationShouldSaveReservation() {
        // Given
        Reservation reservation = new Reservation("Res1", null, null, null, null, null);

        when(reservationRepository.findByIdentifier("Res1")).thenReturn(new ArrayList<>());

        // When
        reservationService.addReservation(reservation);

        // Then
        verify(reservationRepository, never()).findByIdentifier("Res1");
        verify(reservationRepository, times(1)).save(reservation);
    }

//    @Test
//    void addReservationShouldThrowExceptionWhenAddingExistingReservation() {
//        // Given
//        String reservationIdentifier = "Res1";
//        Organization organization = new Organization(1, "Org1");
//        Room room = new Room(1, "Room1", "2.22", 10, true, 10, 10 );
//        Reservation existingReservation = new Reservation(reservationIdentifier, organization, room, LocalDate.of(2023, 5, 1), LocalTime.of(8, 0), LocalTime.of(10, 30));
//
//        when(reservationRepository.findByIdentifier(reservationIdentifier)).thenReturn(Arrays.asList(existingReservation));
//
//        // When/Then
//        assertThrows(IllegalArgumentException.class, () -> {
//            Reservation newReservation = new Reservation(reservationIdentifier, organization, room, LocalDate.of(2023, 6, 1), LocalTime.of(9, 0), LocalTime.of(10, 30));
//            reservationService.addReservation(newReservation);
//        }, "Reservation with the identifier Res1 already exists");
//
//        verify(reservationRepository, never()).save(any(Reservation.class));
//    }

    @Test
    void replaceReservationShouldReplaceReservation() {
        // Given
        long id = 1;
        Reservation existingReservation = new Reservation("Res1", null, null, null, null, null);
        existingReservation.setId(id);
        Reservation newReservation = new Reservation("Updated Res1", null, null, null, null, null);

        when(reservationRepository.existsById(id)).thenReturn(true);

        // When
        reservationService.replaceReservation(id, newReservation);

        // Then
        verify(reservationRepository, times(1)).existsById(id);
        verify(reservationRepository, times(1)).save(newReservation);
        assertEquals(id, newReservation.getId());
    }

    @Test
    void replaceReservationShouldThrowExceptionWhenIdDoesNotExist() {
        // Given
        long id = 1;
        Reservation newReservation = new Reservation("Updated Res1", null, null, null, null, null);

        when(reservationRepository.existsById(id)).thenReturn(false);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> reservationService.replaceReservation(id, newReservation));
        verify(reservationRepository, times(1)).existsById(id);
        verify(reservationRepository, never()).save(newReservation);
    }

    @Test
    void deleteReservationShouldDeleteReservation() {
        // Given
        long id = 1;

        // When
        reservationService.deleteReservation(id);

        // Then
        verify(reservationRepository, times(1)).deleteById(id);
    }
}

