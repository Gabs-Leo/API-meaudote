package com.gabsleo.meaudote.services;

import com.gabsleo.meaudote.dtos.AdoptionAnimalDto;
import com.gabsleo.meaudote.entities.AdoptionAnimal;
import com.gabsleo.meaudote.enums.Model;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.repositories.AdoptionAnimalRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AdoptionAnimalService {
    private final AdoptionAnimalRepository adoptionAnimalRepository;

    @Autowired
    public AdoptionAnimalService(AdoptionAnimalRepository adoptionAnimalRepository) {
        this.adoptionAnimalRepository = adoptionAnimalRepository;
    }
    public AdoptionAnimal findById(UUID id){
        return adoptionAnimalRepository.findById(id).orElseThrow();
    }
    public Page<AdoptionAnimal> findAll(Pageable pageable){
        return adoptionAnimalRepository.findAll(pageable);
    }
    public AdoptionAnimal save(AdoptionAnimal adoptionAnimal){
        return adoptionAnimalRepository.save(adoptionAnimal);
    }
    public AdoptionAnimal update(AdoptionAnimal adoptionAnimal, AdoptionAnimalDto adoptionAnimalDto) {
        UUID petId = adoptionAnimal.getId();
        String imageUrl = adoptionAnimal.getImage();
        BeanUtils.copyProperties(adoptionAnimalDto, adoptionAnimal);
        adoptionAnimal.setId(petId);
        adoptionAnimal.setImage(imageUrl);
        return save(adoptionAnimal);
    }
    public AdoptionAnimal findByIdWhereAppUserEmailEquals(UUID id, String email) throws NotFoundException {
        return adoptionAnimalRepository.findByIdWhereAppUserEmailEquals(id, email).orElseThrow(
                () -> new NotFoundException(Model.ADOPTION_ANIMAL)
        );
    }
    public void deleteById(UUID id) {
        adoptionAnimalRepository.deleteById(id);
    }
    public AdoptionAnimalDto convertToDto(AdoptionAnimal adoptionAnimal){
        return new AdoptionAnimalDto(
                adoptionAnimal.getId(),
                adoptionAnimal.getName(),
                adoptionAnimal.getDescription(),
                adoptionAnimal.getAge(),
                adoptionAnimal.getAdopted(),
                adoptionAnimal.getImage(),
                adoptionAnimal.getCity(),
                adoptionAnimal.getState(),
                adoptionAnimal.getWeight(),
                adoptionAnimal.getSpecies()
        );
    }
}