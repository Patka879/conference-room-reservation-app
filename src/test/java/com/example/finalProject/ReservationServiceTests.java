package com.example.finalProject;

import com.example.finalProject.model.Organization;
import com.example.finalProject.model.Reservation;
import com.example.finalProject.model.ReservationDTO;
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
    void convertToDTOShouldConvertReservationToDTO() {
        // Given
        Organization organization = new Organization();
        organization.setId(1);
        organization.setName("Test Org");

        Room room = new Room();
        room.setId(1);
        room.setName("Test Room");

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setIdentifier("ABC123");
        reservation.setDate(LocalDate.of(2023, 6, 4));
        reservation.setStartTime(LocalTime.of(10, 0));
        reservation.setEndTime(LocalTime.of(12, 0));
        reservation.setRoom(room);
        reservation.setOrganization(organization);

        // When
        ReservationDTO result = reservationService.convertToDTO(reservation);

        // Then
        assertEquals(1, result.getId());
        assertEquals("ABC123", result.getIdentifier());
        assertEquals(LocalDate.of(2023, 6, 4), result.getDate());
        assertEquals(LocalTime.of(10, 0), result.getStartTime());
        assertEquals(LocalTime.of(12, 0), result.getEndTime());
        assertEquals("Test Room", result.getRoomName());
        assertEquals("Test Org", result.getOrganizationName());
    }

    @Test
    void listReservationsShouldReturnListOfReservationDTOs() {
        // Given
        Organization organization = new Organization();
        organization.setId(1);
        organization.setName("Test Org");

        Room room = new Room();
        room.setId(1);
        room.setName("Test Room");

        Reservation reservation1 = new Reservation();
        reservation1.setId(1);
        reservation1.setIdentifier("ABC123");
        reservation1.setDate(LocalDate.of(2023, 6, 4));
        reservation1.setStartTime(LocalTime.of(10, 0));
        reservation1.setEndTime(LocalTime.of(12, 0));
        reservation1.setRoom(room);
        reservation1.setOrganization(organization);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        reservation2.setIdentifier("DEF456");
        reservation2.setDate(LocalDate.of(2023, 6, 5));
        reservation2.setStartTime(LocalTime.of(14, 0));
        reservation2.setEndTime(LocalTime.of(16, 0));
        reservation2.setRoom(room);
        reservation2.setOrganization(organization);

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));

        // When
        List<ReservationDTO> result = reservationService.listReservations();

        // Then
        assertEquals(2, result.size());

        ReservationDTO dto1 = result.get(0);
        assertEquals(1, dto1.getId());
        assertEquals("ABC123", dto1.getIdentifier());
        assertEquals(LocalDate.of(2023, 6, 4), dto1.getDate());
        assertEquals(LocalTime.of(10, 0), dto1.getStartTime());
        assertEquals(LocalTime.of(12, 0), dto1.getEndTime());
        assertEquals("Test Room", dto1.getRoomName());
        assertEquals("Test Org", dto1.getOrganizationName());

        ReservationDTO dto2 = result.get(1);
        assertEquals(2, dto2.getId());
        assertEquals("DEF456", dto2.getIdentifier());
        assertEquals(LocalDate.of(2023, 6, 5), dto2.getDate());
        assertEquals(LocalTime.of(14, 0), dto2.getStartTime());
        assertEquals(LocalTime.of(16, 0), dto2.getEndTime());
        assertEquals("Test Room", dto2.getRoomName());
        assertEquals("Test Org", dto2.getOrganizationName());
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

//    @Test
//    void addReservationShouldSaveReservation() {
//        // Given
//        Reservation reservation = new Reservation("Res1", null, null, null, null, null);
//
//        when(reservationRepository.findByIdentifier("Res1")).thenReturn(new ArrayList<>());
//
//        // When
//        reservationService.addReservation(reservation);
//
//        // Then
//        verify(reservationRepository, never()).findByIdentifier("Res1");
//        verify(reservationRepository, times(1)).save(reservation);
//    }

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


