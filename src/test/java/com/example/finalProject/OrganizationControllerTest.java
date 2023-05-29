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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
}
