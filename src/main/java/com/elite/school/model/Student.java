package com.elite.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
public class Student {

    @Id
    private Long id;
    private String name;
    private String email;



}
