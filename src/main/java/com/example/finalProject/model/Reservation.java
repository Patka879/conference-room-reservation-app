package com.example.finalProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private long id;

    @NotBlank(message = "Reservation identifier is required")
    @Size(min = 2, max = 20)
    @Column(unique = true)
    private String identifier;

    @NotNull(message = "Organization is required")
    @OneToOne
    private Organization organization;

    @NotNull(message = "Room is required")
    @OneToOne
    private Room room;

    @NotNull(message = "Date is required")
    @FutureOrPresent
    private LocalDate date;

    @AssertTrue(message = "Meeting cannot be set later than two weeks in advance")
    private boolean isDateValid() {
        LocalDate currentDate = LocalDate.now();
        LocalDate maxAllowedDate = currentDate.plusWeeks(2);
        return date.isBefore(maxAllowedDate) || date.isEqual(maxAllowedDate);
    }

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @AssertTrue(message = "End time must be after start time")
    private boolean isEndTimeAfterStartTime() {
        return endTime.isAfter(startTime);
    }

    @AssertTrue(message = "Meeting can start only after 6am and before 7pm")
    private boolean isStartTimeValid() {
        return startTime.isAfter(LocalTime.of(5, 59)) && startTime.isBefore(LocalTime.of(19, 1));
    }

    @AssertTrue(message = "Meeting can't take place after 8pm")
    private boolean isEndTimeValid() {
        return endTime.isAfter(LocalTime.of(5, 59)) && endTime.isBefore(LocalTime.of(20, 1));
    }

    public Reservation() {
    }

    public Reservation(String identifier, Organization organization, Room room, LocalDate date,
                       LocalTime startTime, LocalTime endTime) {
        this.identifier = identifier;
        this.organization = organization;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
