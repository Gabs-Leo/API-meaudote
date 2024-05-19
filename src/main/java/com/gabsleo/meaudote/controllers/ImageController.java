package com.gabsleo.meaudote.controllers;

import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.services.AppUserService;
import com.gabsleo.meaudote.services.StorageService;
import com.gabsleo.meaudote.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class ImageController {
    private final StorageService storageService;
    private final AppUserService appUserService;

    @Autowired
    public ImageController(StorageService storageService, AppUserService appUserService) {
        this.storageService = storageService;
        this.appUserService = appUserService;
    }

    @GetMapping("/pets/{id}/image")
    public byte[] downloadPetImage( @PathVariable("id") UUID id ) throws IOException {
        return storageService.download(id, "pets");
    }

    @PostMapping(
        path = "/pets/{id}/image",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Response<String>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable UUID id
    ) throws IOException {
        var response = new Response<String>();
        storageService.save(id, "pets/"+id, file);
        response.setData("File saved.");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/users/{name}/image")
    public byte[] downloadAppUserPorfilePicture( @PathVariable("name") String name ) throws IOException, NotFoundException {
        AppUser appUser = appUserService.findByName(name);
        return storageService.download("users/profile-picture/"+name+"/"+appUser.getProfilePicture());
    }

    @PostMapping(
            path = "/users/{name}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Response<String>> uploadProfileImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable("name") String name
    ) throws IOException, NotFoundException {
        var response = new Response<String>();
        storageService.saveAppUserProfilePicture(name, "users/profile-picture/"+name, file);
        response.setData("File saved.");
        return ResponseEntity.ok().body(response);
    }
}
