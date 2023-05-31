package com.example.finalProject.controler;

import com.example.finalProject.model.Organization;
import com.example.finalProject.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/named/{name}")
    public List<Organization> getOrganizationByName(@PathVariable String name) {
        return organizationService.getOrganizationByName(name);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addOrganization(@RequestBody Organization organization) {
        try {
            organizationService.addOrganization(organization);
            return ResponseEntity.status(HttpStatus.CREATED).body("Organization added successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Organization with the same name already exists");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrganization(@PathVariable long id) {
        organizationService.deleteOrganization(id);
    }

    @PatchMapping("/replace/{id}")
    public void replaceOrganization(@PathVariable long id, @RequestBody Organization newOrganization) {
        organizationService.replaceOrganization(id, newOrganization);
    }
}
