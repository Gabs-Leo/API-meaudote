package com.gabsleo.meaudote.repositories;

import com.gabsleo.meaudote.entities.AdoptionAnimal;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.services.AdoptionAnimalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdoptionAnimalRepository extends JpaRepository<AdoptionAnimal, UUID> {
    Page<AdoptionAnimal> findAll(Pageable pageable);
    Page<AdoptionAnimal> findByAppUser(AppUser appUser, Pageable pageable);
    AdoptionAnimal save(AdoptionAnimal adoptionAnimal);
    @Query("SELECT aa FROM AdoptionAnimal aa WHERE aa.id = :id AND aa.appUser.email = :email")
    Optional<AdoptionAnimal> findByIdWhereAppUserEmailEquals(@Param("id") UUID id, @Param("email") String email);
    void delete(AdoptionAnimal adoptionAnimal);
}
