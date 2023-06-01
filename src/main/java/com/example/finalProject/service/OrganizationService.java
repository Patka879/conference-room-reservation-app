package com.example.finalProject.service;

import com.example.finalProject.model.Organization;
import com.example.finalProject.model.Room;
import com.example.finalProject.repository.OrganizationRepository;
import com.example.finalProject.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    RoomRepository roomRepository;

    public List<Organization> listOrganizations() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> getOrganizationById(long id) {
        return organizationRepository.findById(id);
    }

    public void deleteOrganization(long id) {
        organizationRepository.deleteById(id);
    }

    public List<Organization> getOrganizationByName(String name) {
        return organizationRepository.findByName(name);
    }

    public void addOrganization(Organization organization) {
        String organizationName = organization.getName().toLowerCase();

        List<Organization> existingOrganizations = getOrganizationByName(organizationName);
        if (!existingOrganizations.isEmpty()) {
            String errorMessage = "Organization with the name '" + organization.getName() + "' already exists";
            throw new IllegalArgumentException(errorMessage);
        }

        organization.setName(organizationName);
        organizationRepository.save(organization);
    }

    public void replaceOrganization(long id, Organization newOrganization) {
        if(organizationRepository.existsById(id)) {
            newOrganization.setId(id);
            organizationRepository.save(newOrganization);
        }
        else throw new IllegalArgumentException("Organization doesn't exists with id: " + id);
    }

        public void addRoomToOrganization(long organizationId, long roomId) {
            Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
            Optional<Room> roomOptional = roomRepository.findById(roomId);

            if (organizationOptional.isPresent() && roomOptional.isPresent()) {
                Organization organization = organizationOptional.get();
                Room room = roomOptional.get();
                room.setOrganization(organization);
                organization.getRooms().add(room);
                roomRepository.save(room);
                organizationRepository.save(organization);
            } else {
                throw new IllegalArgumentException("Organization or Room not found");
            }
    }
}
