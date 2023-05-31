package com.example.finalProject.model;

import jakarta.persistence.*;

@Entity
@Table(name="organization")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private long id;
}
