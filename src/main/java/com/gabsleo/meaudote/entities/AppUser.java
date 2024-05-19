package com.gabsleo.meaudote.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class AppUser implements UserDetails {
    @Id
    private String cpf;
    private String name;
    private String password;
    private String email;
    @Column(name = "created_at")
    private Date createdAt;
    private String phone;
    @Column(name = "profile_picture")
    private String profilePicture;
    @Column(name = "banner_picture")
    private String bannerPicture;
    @Column(name = "is_ngo")
    private Boolean isNGO;
    private String state;
    private String city;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "appuser_cpf"),
        inverseJoinColumns = @JoinColumn(name = "approle_id")
    )
    private final List<AppRole> appRoles = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<PixKey> pixKeys = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<AdoptionAnimal> adoptionAnimals = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<DonationPage> donationPages = new ArrayList<>();

    public AppUser() {}
    public AppUser(String cpf, String name, String password, String email, Date createdAt, String phone, String profilePicture, String bannerPicture, Boolean isNGO, String state, String city) {
        this.cpf = cpf;
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.bannerPicture = bannerPicture;
        this.isNGO = isNGO;
        this.state = state;
        this.city = city;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authors = new ArrayList<>();
        this.getAppRoles().forEach(i ->
                authors.add(new SimpleGrantedAuthority(i.getName()))
        );
        return authors;
    }
    @Override
    public String getPassword() {
        return password;
    }

    public AppUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AppUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public AppUser setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public String getBannerPicture() {
        return bannerPicture;
    }

    public AppUser setBannerPicture(String bannerPicture) {
        this.bannerPicture = bannerPicture;
        return this;
    }

    public Boolean getNGO() {
        return isNGO;
    }

    public void setNGO(Boolean NGO) {
        isNGO = NGO;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<PixKey> getPixKeys() {
        return pixKeys;
    }

    public List<AdoptionAnimal> getAdoptionAnimals() {
        return adoptionAnimals;
    }

    public List<DonationPage> getDonationPages() {
        return donationPages;
    }

    public String getName() {
        return name;
    }

    public List<AppRole> getAppRoles() {
        return appRoles;
    }

}
