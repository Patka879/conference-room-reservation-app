package com.example.finalProject.service;

import com.example.finalProject.model.Equipment;
import com.example.finalProject.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    @Autowired
    EquipmentRepository equipmentRepository;

    public List<Equipment> listEquipments() {
        return equipmentRepository.findAll();
    }

    public Optional<Equipment> getEquipmentById(long id) {
        return equipmentRepository.findById(id);
    }

    public void deleteEquipment(long id) {
        equipmentRepository.deleteById(id);
    }

    public List<Equipment> getEquipmentByName(String name) {
        return equipmentRepository.findByName(name);
    }

    public void addEquipment(Equipment equipment) {
        String equipmentName = equipment.getName().toLowerCase();

        List<Equipment> existingEquipments = getEquipmentByName(equipmentName);
        if (!existingEquipments.isEmpty()) {
            String errorMessage = "Equipment with the name '" + equipment.getName() + "' already exists";
            throw new IllegalArgumentException(errorMessage);
        }

        equipment.setName(equipmentName);
        equipmentRepository.save(equipment);
    }

    public void replaceEquipment(long id, Equipment newEquipment) {
        if(equipmentRepository.existsById(id)) {
            newEquipment.setId(id);
            equipmentRepository.save(newEquipment);
        }
        else throw new IllegalArgumentException("Equipment doesn't exists with id: " + id);
    }
}
