package com.example.finalProject.controler;

import com.example.finalProject.model.Equipment;
import com.example.finalProject.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipment")
@CrossOrigin(origins="http://localhost")
public class EquipmentController {
    @Autowired
    EquipmentService equipmentService;

    @GetMapping("/all")
    public List<Equipment> listEquipments() {
        return equipmentService.listEquipments();
    }

    @GetMapping("/{id}")
    public Optional<Equipment> getEquipmentById(@PathVariable long id) {
        return Optional.ofNullable(equipmentService.getEquipmentById(id));
    }

    @GetMapping("/named/{name}")
    public List<Equipment> getEquipmentByName(@PathVariable String name) {
        return equipmentService.getEquipmentByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void addEquipment(@RequestBody Equipment equipment) {
        equipmentService.addEquipment(equipment);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEquipment(@PathVariable long id) {
        equipmentService.deleteEquipment(id);
    }

    @PatchMapping("/replace/{id}")
    public void replaceEquipment(@PathVariable long id, @RequestBody Equipment newEquipment) {
        equipmentService.replaceEquipment(id, newEquipment);
    }
}
