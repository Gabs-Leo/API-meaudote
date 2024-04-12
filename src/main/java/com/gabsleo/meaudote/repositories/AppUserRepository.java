package com.gabsleo.meaudote.repositories;

import com.gabsleo.meaudote.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByEmail(String email);
    AppUser save(AppUser appUser);
}
