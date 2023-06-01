package com.example.finalProject;

import com.example.finalProject.model.Room;
import com.example.finalProject.repository.RoomRepository;
import com.example.finalProject.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RoomControllerApplicationTests {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listRoomShouldReturnAllRooms() {
        // Given
        Room room1 = new Room(1, "Room1", "R001", 1, true, 10, 20);
        Room room2 = new Room(2, "Room2", "R002", 2, false, 15, 25);
        List<Room> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);

        when(roomRepository.findAll()).thenReturn(rooms);

        // When
        List<Room> result = roomService.listRoom();

        // Then
        assertEquals(rooms, result);
    }

    @Test
    void getRoomByIdShouldReturnRoom() {
        // Given
        long id = 1;
        Room room = new Room(id, "Room1", "R001", 1, true, 10, 20);

        when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        // When
        Optional<Room> result = roomService.getRoomById(id);

        // Then
        assertEquals(Optional.of(room), result);
    }

    @Test
    void getRoomByIdShouldReturnEmptyOptionalIfRoomDoesNotExist() {
        // Given
        long id = 1;

        when(roomRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Room> result = roomService.getRoomById(id);

        // Then
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void addRoomShouldSaveRoomWhenAddingNewRoom() {
        // Given
        long id = 1;
        Room room = new Room(id, "Room1", "R001", 1, true, 10, 20);

        when(roomRepository.findByName("Room1")).thenReturn(Collections.emptyList());

        // When
        roomService.addRoom(room);

        // Then
        verify(roomRepository, never()).findById(id);
        verify(roomRepository, times(1)).save(room);
    }


    @Test
    public void addRoomShouldThrowExceptionWhenAddingExistingRoom() {
        // Given
        Room existingRoom = new Room();
        existingRoom.setName("Room1");

        when(roomRepository.findByName("Room1")).thenReturn(Arrays.asList(existingRoom));

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            roomService.addRoom(existingRoom);
        });

        assertEquals("Room with the name 'Room1' already exists", exception.getMessage());
        verify(roomRepository, never()).save(existingRoom);
    }


    @Test
    void replaceRoomShouldReplaceRoom() {
        // Given
        long id = 1;
        Room existingRoom = new Room(id, "Room1", "R001", 1, true, 10, 20);
        Room newRoom = new Room(id, "Updated Room1", "R002", 2, false, 15, 25);

        when(roomRepository.existsById(id)).thenReturn(true);

        // When
        roomService.replaceRoom(id, newRoom);

        // Then
        verify(roomRepository, times(1)).existsById(id);
        verify(roomRepository, times(1)).save(newRoom);
        assertEquals(id, newRoom.getId());
    }

    @Test
    void replaceRoomShouldThrowExceptionWhenIdIsNotExisting() {
        // Given
        long id = 1;
        Room newRoom = new Room(id, "Updated Room1", "R002", 2, false, 15, 25);

        when(roomRepository.existsById(id)).thenReturn(false);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> roomService.replaceRoom(id, newRoom));
        verify(roomRepository, times(1)).existsById(id);
        verify(roomRepository, never()).save(newRoom);
    }
}
