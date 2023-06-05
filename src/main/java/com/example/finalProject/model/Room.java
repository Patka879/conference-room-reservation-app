package com.example.finalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Table(name="room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private long id;

    @NotBlank(message = "Conference room name is required")
    @Size(min = 2, max = 20)
    private String name;

    @Pattern(regexp = "^\\d+(\\.\\d+)?$", message = "Identifier format is invalid")
    private String identifier;
    @NotNull(message = "Level is required")
    @Range(min = 0, max = 10, message = "Level must be between 0 and 10")
    private int level;

    @NotNull(message = "Availability is required")
    private boolean availability;

    @NotNull(message = "Number of sitting places is required")
    private Integer numberOfSittingPlaces;

    @NotNull(message = "Number of standing places is required")
    private Integer numberOfStandingPlaces;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "organization_id", columnDefinition = "integer")
    public Organization organization;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;


    public Room() {
    }

    public Room(long id, String name, String identifier, int level, boolean availability, Integer numberOfSittingPlaces, Integer numberOfStandingPlaces) {
        this.id = id;
        this.name = name;
        this.identifier = identifier;
        this.level = level;
        this.availability = availability;
        this.numberOfSittingPlaces = numberOfSittingPlaces;
        this.numberOfStandingPlaces = numberOfStandingPlaces;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Integer getNumberOfSittingPlaces() {
        return numberOfSittingPlaces;
    }

    public void setNumberOfSittingPlaces(Integer numberOfSittingPlaces) {
        this.numberOfSittingPlaces = numberOfSittingPlaces;
    }

    public Integer getNumberOfStandingPlaces() {
        return numberOfStandingPlaces;
    }

    public void setNumberOfStandingPlaces(Integer numberOfStandingPlaces) {
        this.numberOfStandingPlaces = numberOfStandingPlaces;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
