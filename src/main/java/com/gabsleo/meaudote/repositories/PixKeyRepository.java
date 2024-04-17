package com.gabsleo.meaudote.repositories;

import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.entities.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKey, UUID> {
    Optional<PixKey> findByCode(String code);
    List<PixKey> findByAppUser(AppUser appUser);
    @Query("SELECT pk FROM PixKey pk WHERE pk.id = :id AND pk.appUser.email = :email")
    Optional<PixKey> findByIdWhereAppUserEquals(UUID id, String email);
    PixKey save(PixKey pixKey);
    void delete(PixKey pixKey);
}
