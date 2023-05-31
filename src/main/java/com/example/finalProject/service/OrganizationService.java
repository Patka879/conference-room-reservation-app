package com.example.finalProject.service;

import com.example.finalProject.model.Organization;
import com.example.finalProject.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

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
        if (!getOrganizationById(organization.getId()).isPresent()) {
            organizationRepository.save(organization);
        }
        else throw new IllegalArgumentException("Organization " + organization.getId() + " already exists");
    }

    public void replaceOrganization(long id, Organization newOrganization) {
        if(organizationRepository.existsById(id)) {
            newOrganization.setId(id);
            organizationRepository.save(newOrganization);
        }
        else throw new IllegalArgumentException("Organization doesn't exists with id: " + id);
    }
}
