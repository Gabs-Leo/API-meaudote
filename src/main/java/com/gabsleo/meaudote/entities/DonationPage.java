package com.gabsleo.meaudote.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class DonationPage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String description;
    @Column(name = "wanted_value")
    private BigDecimal wantedValue;
    @Column(name = "collected_value")
    private BigDecimal collectedValue;
    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser appUser;
}
