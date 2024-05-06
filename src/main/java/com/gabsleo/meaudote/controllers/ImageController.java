package com.gabsleo.meaudote.controllers;

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

    @Autowired
    public ImageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/pets/{id}/image")
    public byte[] downloadCompanyLogo( @PathVariable("id") UUID id ) throws IOException {
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
}
