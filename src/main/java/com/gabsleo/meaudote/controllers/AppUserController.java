package com.gabsleo.meaudote.controllers;

import com.gabsleo.meaudote.dtos.AppUserDto;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.exceptions.AppUserNotFoundException;
import com.gabsleo.meaudote.exceptions.AppUserNotLoggedException;
import com.gabsleo.meaudote.services.AppUserService;
import com.gabsleo.meaudote.utils.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Response<AppUserDto>> getCurrent(Principal principal) throws AppUserNotLoggedException, AppUserNotFoundException {
        if(principal == null){
            throw new AppUserNotLoggedException();
        }
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
}
