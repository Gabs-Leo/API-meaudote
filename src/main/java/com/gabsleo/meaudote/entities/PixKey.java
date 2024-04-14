package com.gabsleo.meaudote.entities;

import com.gabsleo.meaudote.enums.PixKeyType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class PixKey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String code;
    @Enumerated(EnumType.STRING)
    private PixKeyType type;
    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser appUser;
}
