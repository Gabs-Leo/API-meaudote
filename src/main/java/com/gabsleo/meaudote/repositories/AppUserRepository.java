package com.gabsleo.meaudote.repositories;

import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.exceptions.AppUserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByEmail(String email);
    AppUser save(AppUser appUser);
    Optional<AppUser> findByCpf(String cpf);
}

