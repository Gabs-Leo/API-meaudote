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

    public AdoptionAnimal () {}
    public AdoptionAnimal(UUID id, String name, String description, Integer age, Boolean adopted, String image, String city, String state, Float weight, Species species, AppUser appUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.age = age;
        this.adopted = adopted;
        this.image = image;
        this.city = city;
        this.state = state;
        this.weight = weight;
        this.species = species;
        this.appUser = appUser;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getAdopted() {
        return adopted;
    }

    public void setAdopted(Boolean adopted) {
        this.adopted = adopted;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public AdoptionAnimal setAppUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }
}
