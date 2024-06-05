package com.gabsleo.meaudote.controllers;

import com.gabsleo.meaudote.dtos.*;
import com.gabsleo.meaudote.entities.AdoptionAnimal;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.entities.PixKey;
import com.gabsleo.meaudote.exceptions.AppUserNotLoggedException;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.services.AdoptionAnimalService;
import com.gabsleo.meaudote.services.AppUserService;
import com.gabsleo.meaudote.services.PixKeyService;
import com.gabsleo.meaudote.utils.Response;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class AppUserController {

    private final AppUserService appUserService;
    private final AdoptionAnimalService adoptionAnimalService;
    private final PixKeyService pixKeyService;

    @Autowired
    public AppUserController(AppUserService appUserService, AdoptionAnimalService adoptionAnimalService, PixKeyService pixKeyService) {
        this.appUserService = appUserService;
        this.adoptionAnimalService = adoptionAnimalService;
        this.pixKeyService = pixKeyService;
    }

    @GetMapping("/current")
    public ResponseEntity<Response<AppUserDto>> getCurrent(Principal principal) throws AppUserNotLoggedException, NotFoundException {
        Response<AppUserDto> response = new Response<>();
        AppUser appUser = appUserService.findByEmail(principal.getName());
        AppUserDto userDto = appUserService.convertToDto(appUser);
        response.setData(userDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Response<AppUserDto>> getUser(@PathVariable("username") String username) throws NotFoundException {
        Response<AppUserDto> response = new Response<>();
        AppUser appUser = appUserService.findByName(username);
        AppUserDto userDto = appUserService.convertToDto(appUser);
        response.setData(userDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<Page<AppUserDto>>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "size", defaultValue = "20") int objectsPerPage
        ) {
        Response<Page<AppUserDto>> response = new Response<>();
        Pageable request = PageRequest.of(page, objectsPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<AppUser> userPage = appUserService.findAll(request);
        Page<AppUserDto> userPageDto = userPage.map(appUserService::convertToDto);
        response.setData(userPageDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current/pets")
    public ResponseEntity<Response<Page<AdoptionAnimalDto>>> getAdoptionAnimals(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "size", defaultValue = "20") int objectsPerPage,
            Principal principal
    ) throws NotFoundException, AppUserNotLoggedException {
        Response<Page<AdoptionAnimalDto>> response = new Response<>();
        Pageable request = PageRequest.of(page, objectsPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<AdoptionAnimal> petPage = adoptionAnimalService.findByAppUser(principal, request);
        Page<AdoptionAnimalDto> petPageDto = petPage.map(adoptionAnimalService::convertToDto);
        response.setData(petPageDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/pets")
    public ResponseEntity<Response<Page<AdoptionAnimalDto>>> getAdoptionAnimalsByAnyUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "size", defaultValue = "20") int objectsPerPage,
            @PathVariable("username") String username
    ) throws NotFoundException, AppUserNotLoggedException {
        Response<Page<AdoptionAnimalDto>> response = new Response<>();
        Pageable request = PageRequest.of(page, objectsPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<AdoptionAnimal> petPage = adoptionAnimalService.findByAppUser(appUserService.findByName(username), request);
        Page<AdoptionAnimalDto> petPageDto = petPage.map(adoptionAnimalService::convertToDto);
        response.setData(petPageDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current/pixkeys")
    public ResponseEntity<Response<List<PixKeyDto>>> getPixKeys(Principal principal ) throws NotFoundException, AppUserNotLoggedException {
        Response<List<PixKeyDto>> response = new Response<>();
        List<PixKey> keys = pixKeyService.findByAppUser(principal);
        List<PixKeyDto> keysDto = keys.stream().map(pixKeyService::convertToDto).toList();
        response.setData(keysDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/pixkeys")
    public ResponseEntity<Response<List<PixKeyDto>>> getPixKeysByUser(@PathParam("username") String username) throws NotFoundException, AppUserNotLoggedException {
        Response<List<PixKeyDto>> response = new Response<>();
        List<PixKey> keys = pixKeyService.findByAppUser(appUserService.findByName(username));
        List<PixKeyDto> keysDto = keys.stream().map(pixKeyService::convertToDto).toList();
        response.setData(keysDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/current")
    public ResponseEntity<Response<AppUserDto>> updateCurrent(Principal principal, @RequestBody UpdateAppUserDto appUserDto) throws AppUserNotLoggedException, NotFoundException {
        Response<AppUserDto> response = new Response<>();
        AppUser appUser = appUserService.findByEmail(principal.getName());
        AppUser result = appUserService.update(appUser, appUserDto);
        response.setData(appUserService.convertToDto(result));
        return ResponseEntity.ok(response);
    }
}
