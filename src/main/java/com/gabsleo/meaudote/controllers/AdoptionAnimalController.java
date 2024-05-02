package com.gabsleo.meaudote.controllers;

import com.gabsleo.meaudote.dtos.AdoptionAnimalDto;
import com.gabsleo.meaudote.entities.AdoptionAnimal;
import com.gabsleo.meaudote.exceptions.AppUserNotLoggedException;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.services.AdoptionAnimalService;
import com.gabsleo.meaudote.services.AppUserService;
import com.gabsleo.meaudote.utils.Response;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pets")
@CrossOrigin("*")
public class AdoptionAnimalController {
    private final AdoptionAnimalService adoptionAnimalService;
    private final AppUserService appUserService;

    @Autowired
    public AdoptionAnimalController(AdoptionAnimalService adoptionAnimalService, AppUserService appUserService) {
        this.adoptionAnimalService = adoptionAnimalService;
        this.appUserService = appUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<AdoptionAnimalDto>> findById(@PathParam("id") UUID id) {
        Response<AdoptionAnimalDto> response = new Response<>();
        response.setData(
                adoptionAnimalService.convertToDto(adoptionAnimalService.findById(id))
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<Page<AdoptionAnimalDto>>> findAllAdoptionAnimalDto(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "size", defaultValue = "20") int objectsPerPage
    ) {
        Response<Page<AdoptionAnimalDto>> response = new Response<>();
        Pageable request = PageRequest.of(page, objectsPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<AdoptionAnimal> pageAdopt = adoptionAnimalService.findAll(request);
        Page<AdoptionAnimalDto> pageAdoptDto = pageAdopt.map(adoptionAnimalService::convertToDto);
        response.setData(pageAdoptDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<AdoptionAnimalDto>> save(Principal principal, @RequestBody AdoptionAnimalDto adoptionAnimalDto) throws AppUserNotLoggedException, NotFoundException {
        if(principal == null){
            throw new AppUserNotLoggedException();
        }
        AdoptionAnimal adoptionAnimal = new AdoptionAnimal();
        Response<AdoptionAnimalDto> response = new Response<>();

        BeanUtils.copyProperties(adoptionAnimalDto, adoptionAnimal);
        response.setData(adoptionAnimalService.convertToDto(adoptionAnimalService.save(adoptionAnimal.setAppUser(appUserService.findByEmail(principal.getName())))));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<AdoptionAnimalDto>> update(Principal principal, @PathVariable UUID id, @RequestBody AdoptionAnimalDto adoptionAnimalDto) throws AppUserNotLoggedException, NotFoundException {
        if(principal == null){
            throw new AppUserNotLoggedException();
        }
        AdoptionAnimal adoptionAnimal = adoptionAnimalService.findByIdWhereAppUserEmailEquals(id, principal.getName());
        Response<AdoptionAnimalDto> response = new Response<>();

        response.setData(adoptionAnimalService.convertToDto(adoptionAnimalService.update(adoptionAnimal, adoptionAnimalDto)));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(Principal principal, @PathVariable UUID id) throws AppUserNotLoggedException, NotFoundException {
        if(principal == null){
            throw new AppUserNotLoggedException();
        }
        AdoptionAnimal adoptionAnimal = adoptionAnimalService.findByIdWhereAppUserEmailEquals(id, principal.getName());
        Response<String> response = new Response<>();
        adoptionAnimalService.deleteById(id);
        response.setData("Success.");
        return ResponseEntity.ok(response);
    }
}
