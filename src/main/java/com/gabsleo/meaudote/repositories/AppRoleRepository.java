package com.gabsleo.meaudote.repositories;

import com.gabsleo.meaudote.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, UUID> {
    AppRole save(AppRole appRole);
    Optional<AppRole> findByName(String name);
}
