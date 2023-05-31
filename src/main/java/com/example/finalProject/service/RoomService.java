package com.example.finalProject.service;

import com.example.finalProject.model.Organization;
import com.example.finalProject.model.Room;
import com.example.finalProject.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<Room> listRoom() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(long id) {
        return roomRepository.findById(id);
    }

    public List<Room> getRoomByName(String name) {
        return roomRepository.findByName(name);
    }

    public void deleteRoom(long id) {
        roomRepository.deleteById(id);
    }

    public void addRoom(Room room) {
        List<Room> existingRooms = getRoomByName(room.getName());
        if (!existingRooms.isEmpty()) {
            String errorMessage = "Room with the name '" + room.getName() + "' already exists";
            throw new IllegalArgumentException(errorMessage);
        }
        roomRepository.save(room);
    }

    public void replaceRoom(long id, Room newRoom) {
        if(roomRepository.existsById(id)) {
            newRoom.setId(id);
            roomRepository.save(newRoom);
        }
        else throw new IllegalArgumentException("Room doesn't exists with id: " + id);
    }
}
