package com.example.finalProject;

import com.example.finalProject.model.Equipment;
import com.example.finalProject.model.EquipmentType;
import com.example.finalProject.repository.EquipmentRepository;
import com.example.finalProject.service.EquipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class EquipmentTests {

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private EquipmentService equipmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listEquipmentsShouldReturnListOfEquipments() {
        // Given
        Equipment equipment1 = new Equipment(1, "Equipment 1", EquipmentType.PROJECTOR);
        Equipment equipment2 = new Equipment(2, "Equipment 2", EquipmentType.PHONE);
        when(equipmentRepository.findAll()).thenReturn(Arrays.asList(equipment1, equipment2));

        // When
        List<Equipment> result = equipmentService.listEquipments();

        // Then
        assertEquals(2, result.size());
    }
}