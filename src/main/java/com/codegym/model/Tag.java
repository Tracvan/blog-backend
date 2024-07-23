package com.codegym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Tag {
    @Id
    private Long id;
    private String name;

}
