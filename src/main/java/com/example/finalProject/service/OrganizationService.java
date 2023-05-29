package com.example.finalProject.service;

import com.example.finalProject.model.Organization;
import com.example.finalProject.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    public List<Organization> listOrganizations() {
        return organizationRepository.findAll();
    }
}
