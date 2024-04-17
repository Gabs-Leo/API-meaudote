package com.gabsleo.meaudote.controllers;

import com.gabsleo.meaudote.dtos.AdoptionAnimalDto;
import com.gabsleo.meaudote.dtos.AppUserDto;
import com.gabsleo.meaudote.entities.AdoptionAnimal;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.exceptions.AppUserNotLoggedException;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.services.AppUserService;
import com.gabsleo.meaudote.utils.Response;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/current")
    public ResponseEntity<Response<AppUserDto>> getCurrent(Principal principal) throws AppUserNotLoggedException, NotFoundException {
        Response<AppUserDto> response = new Response<>();
        AppUser appUser = appUserService.findByEmail(principal.getName());
        AppUserDto userDto = new AppUserDto(
            appUser.getName(),
            appUser.getPhone(),
            appUser.getProfilePicture(),
            appUser.getBannerPicture(),
            appUser.getState(),
            appUser.getCity()
        );
        response.setData(userDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Response<AppUserDto>> getUser(@PathParam("username") String username) throws NotFoundException {
        Response<AppUserDto> response = new Response<>();
        AppUser appUser = appUserService.findByName(username);
        AppUserDto userDto = new AppUserDto(
                appUser.getName(),
                appUser.getPhone(),
                appUser.getProfilePicture(),
                appUser.getBannerPicture(),
                appUser.getState(),
                appUser.getCity()
        );
        response.setData(userDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<Page<AppUserDto>>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "size", defaultValue = "20") int objectsPerPage)
    {
        Response<Page<AppUserDto>> response = new Response<>();
        Pageable request = PageRequest.of(page, objectsPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<AppUser> userPage = appUserService.findAll(request);
        Page<AppUserDto> userPageDto = userPage.map(appUserService::convertToDto);

        response.setData(userPageDto);
        return ResponseEntity.ok(response);
    }
}
