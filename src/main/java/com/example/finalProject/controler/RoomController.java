package com.example.finalProject.controler;


import com.example.finalProject.model.Room;
import com.example.finalProject.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;  
import java.util.Optional;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins="http://localhost:4200")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("/all")
    public List<Room> listRooms() {
        return roomService.listRoom();
    }

    @GetMapping("/{id}")
    public Optional<Room> getRoomById(@PathVariable long id) {
        return roomService.getRoomById(id);
    }

    @GetMapping("/named/{name}")
    public List<Room> getRoomByName(@PathVariable String name) {
        return roomService.getRoomByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void addRoom(@RequestBody Room room) {
        roomService.addRoom(room);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRoom(@PathVariable long id) {
        roomService.deleteRoom(id);
    }

    @PatchMapping("/replace/{id}")
    public void replaceRoom(@PathVariable long id, @RequestBody Room newRoom) {
        roomService.replaceRoom(id, newRoom);
    }
}
