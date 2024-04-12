package com.gabsleo.meaudote.entities;

import com.gabsleo.meaudote.enums.Species;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class AdoptionAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;
    private Integer age;
    private Boolean adopted;
    private String image;
    private String city;
    private String state;
    private Float weight;

    @Enumerated(EnumType.STRING)
    private Species species;
    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser appUser;

}
