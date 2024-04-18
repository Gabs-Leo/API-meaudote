package com.gabsleo.meaudote.controllers;

import com.gabsleo.meaudote.dtos.PixKeyDto;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.entities.PixKey;
import com.gabsleo.meaudote.exceptions.AppUserNotLoggedException;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.services.AppUserService;
import com.gabsleo.meaudote.services.PixKeyService;
import com.gabsleo.meaudote.utils.Response;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pixkeys")
public class PixKeyController {
    private final PixKeyService pixKeyService;
    private final AppUserService appUserService;

    @Autowired
    public PixKeyController(PixKeyService pixKeyService, AppUserService appUserService) {
        this.pixKeyService = pixKeyService;
        this.appUserService = appUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<PixKeyDto>> getFromId(@PathParam("id") UUID id) throws NotFoundException, AppUserNotLoggedException {
        Response<PixKeyDto> response = new Response<>();
        response.setData(
                pixKeyService.convertToDto(pixKeyService.findById(id))
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<PixKeyDto>> save(@RequestBody PixKeyDto pixKeyDto, Principal principal) throws NotFoundException, AppUserNotLoggedException {
        Response<PixKeyDto> response = new Response<>();
        PixKey pixKey = new PixKey();
        BeanUtils.copyProperties(pixKeyDto, pixKey);
        pixKey.setAppUser(appUserService.findByEmail(principal));
        response.setData(
            pixKeyService.convertToDto(pixKeyService.save(pixKey))
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<PixKeyDto>> update(@RequestBody PixKeyDto pixKeyDto, @PathParam("id") UUID id, Principal principal) throws NotFoundException, AppUserNotLoggedException {
        Response<PixKeyDto> response = new Response<>();
        AppUser appUser = appUserService.findByEmail(principal);
        PixKey key = pixKeyService.findByIdWhereAppUserEquals(id, appUser);
        BeanUtils.copyProperties(pixKeyDto, key);
        key.setId(id);
        response.setData(
            pixKeyService.convertToDto(pixKeyService.save(key))
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathParam("id") UUID id, Principal principal) throws NotFoundException, AppUserNotLoggedException {
        Response<String> response = new Response<>();
        AppUser appUser = appUserService.findByEmail(principal);
        PixKey key = pixKeyService.findByIdWhereAppUserEquals(id, appUser);

        response.setData("Success.");
        return ResponseEntity.ok(response);
    }
}
