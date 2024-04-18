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

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public PixKeyType getType() {
        return type;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(PixKeyType type) {
        this.type = type;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
