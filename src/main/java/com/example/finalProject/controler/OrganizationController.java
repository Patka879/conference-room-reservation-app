package com.example.finalProject.controler;

import com.example.finalProject.model.Organization;
import com.example.finalProject.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/organization")
@CrossOrigin(origins="http://localhost:4200")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @GetMapping("/all")
    public List<Organization> listOrganizations() {
        return organizationService.listOrganizations();
    }

    @GetMapping("/{id}")
    public Optional<Organization> getOrganizationById(@PathVariable long id) {
        return organizationService.getOrganizationById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void addOrganization(@RequestBody Organization organization) {
        organizationService.addOrganization(organization);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrganization(@PathVariable long id) {
        organizationService.deleteOrganization(id);
    }

    @PatchMapping("/{id}/replace")
    public void replaceOrganization(@PathVariable long id, @RequestBody Organization newOrganization) {
        organizationService.replaceOrganization(id, newOrganization);
    }
}
