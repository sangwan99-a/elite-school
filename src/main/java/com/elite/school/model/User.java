package com.elite.school.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    // Getters and Setters
}
