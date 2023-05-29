package com.example.finalProject;

import com.example.finalProject.model.Organization;
import com.example.finalProject.repository.OrganizationRepository;
import com.example.finalProject.service.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void getOrganizationById_ExistingId_ShouldReturnOrganization() {
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
    void getOrganizationById_NonExistingId_ShouldReturnEmptyOptional() {
        // Given
        long id = 1;

        when(organizationRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Organization> result = organizationService.getOrganizationById(id);

        // Then
        assertEquals(Optional.empty(), result);
    }

    @Test
    void addOrganization_NewOrganization_ShouldSaveOrganization() {
        // Given
        long id = 1;
        Organization org = new Organization(id, "Org1");

        when(organizationRepository.findById(id)).thenReturn(Optional.empty());

        // When
        organizationService.addOrganization(org);

        // Then
        verify(organizationRepository, times(1)).findById(id);
        verify(organizationRepository, times(1)).save(org);
    }

    @Test
    void addOrganization_ExistingOrganization_ShouldThrowException() {
        // Given
        long id = 1;
        Organization org = new Organization(id, "Org1");

        when(organizationRepository.findById(id)).thenReturn(Optional.of(org));

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> organizationService.addOrganization(org));
        verify(organizationRepository, times(1)).findById(id);
        verify(organizationRepository, never()).save(org);
    }

    @Test
    void replaceOrganization_ExistingId_ShouldReplaceOrganization() {
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
    void replaceOrganization_NonExistingId_ShouldThrowException() {
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
