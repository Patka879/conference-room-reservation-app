package com.example.finalProject.controler;

import com.example.finalProject.model.Organization;
import com.example.finalProject.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @GetMapping("/all")
    public List<Organization> listOrganizations() {
        return organizationService  .listOrganizations();
    }
}
