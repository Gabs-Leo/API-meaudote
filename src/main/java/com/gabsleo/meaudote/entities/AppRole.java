package com.gabsleo.meaudote.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;

    @ManyToMany(mappedBy = "")
    private List<AppUser> appUsers = new ArrayList<>();
}
