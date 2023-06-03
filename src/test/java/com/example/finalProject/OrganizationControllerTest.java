package com.example.finalProject;

import com.example.finalProject.model.Organization;
import com.example.finalProject.model.Room;
import com.example.finalProject.repository.OrganizationRepository;
import com.example.finalProject.repository.RoomRepository;
import com.example.finalProject.service.OrganizationService;
import com.example.finalProject.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrganizationControllerTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listOrganizationsShouldReturnAllOrganizations() {
        // Given
        Organization org1 = new Organization(1, "Org1");
        Organization org2 = new Organization(2  , "Org2");
        List<Organization> organizations = new ArrayList<>();
        organizations.add(org1);
        organizations.add(org2);

        when(organizationRepository.findAll()).thenReturn(organizations);

        // When
        List<Organization> result = organizationService.listOrganizations();

        // Then
        assertEquals(organizations, result);
    }

    @Test
    void getOrganizationByIdShouldReturnOrganization() {
        // Given
        long id = 1;
        Organization org = new Organization(id, "Org1");

        when(organizationRepository.findById(id)).thenReturn(Optional.of(org));

        // When
        Optional<Organization> result = organizationService.getOrganizationById(id);

        // Then
        assertEquals(Optional.of(org), result);
    }

    @Test
    void getOrganizationByIdShouldReturnEmptyOptionalWhenIdDoesntExists() {
        // Given
        long id = 1;

        when(organizationRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Organization> result = organizationService.getOrganizationById(id);

        // Then
        assertEquals(Optional.empty(), result);
    }

    @Test
    void addOrganizationShouldSaveOrganization() {
        // Given
        long id = 1;
        Organization org = new Organization(id, "Org1");

        when(organizationRepository.findByName("Org1")).thenReturn(Collections.emptyList());

        // When
        organizationService.addOrganization(org);

        // Then
        verify(organizationRepository, never()).findById(id);
        verify(organizationRepository, times(1)).save(org);
    }

    @Test
    public void addOrganizationShouldThrowExceptionWhenAddingExistingOrganization() {
        // Given
        Organization existingOrganization = new Organization();
        existingOrganization.setName("Org1");

        when(organizationRepository.findByName("Org1")).thenReturn(Arrays.asList(existingOrganization));

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            organizationService.addOrganization(existingOrganization);
        });

        assertEquals("Organization with the name 'Org1' already exists", exception.getMessage());
        verify(organizationRepository, never()).save(existingOrganization);
    }

    @Test
    void addOrganizationShouldThrowExceptionWhenAddingExistingOrganizationWithDifferentCase() {
        // Given
        Organization existingOrganization = new Organization();
        existingOrganization.setName("Org1");

        when(organizationRepository.findByName("org1")).thenReturn(Arrays.asList(existingOrganization));

        Organization newOrganization = new Organization();
        newOrganization.setName("org1");

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            organizationService.addOrganization(newOrganization);
        });

        assertEquals("Organization with the name 'org1' already exists", exception.getMessage());
        verify(organizationRepository, never()).save(newOrganization);
    }



    @Test
    void replaceOrganizationShouldReplaceOrganization() {
        // Given
        long id = 1;
        Organization existingOrg = new Organization(id, "Org1");
        Organization newOrg = new Organization(id, "Updated Org1");

        when(organizationRepository.existsById(id)).thenReturn(true);

        // When
        organizationService.replaceOrganization(id, newOrg);

        // Then
        verify(organizationRepository, times(1)).existsById(id);
        verify(organizationRepository, times(1)).save(newOrg);
        assertEquals(id, newOrg.getId());
    }

    @Test
    void replaceOrganizationShouldThrowExceptionWhenIdDoestExists() {
        // Given
        long id = 1;
        Organization newOrg = new Organization(id, "Updated Org1");

        when(organizationRepository.existsById(id)).thenReturn(false);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> organizationService.replaceOrganization(id, newOrg));
        verify(organizationRepository, times(1)).existsById(id);
        verify(organizationRepository, never()).save(newOrg);
    }

}
