package com.gabsleo.meaudote.controllers;

import com.gabsleo.meaudote.dtos.JwtTokenDto;
import com.gabsleo.meaudote.dtos.LoginDto;
import com.gabsleo.meaudote.dtos.RegisterDto;
import com.gabsleo.meaudote.exceptions.AppUserNotFoundException;
import com.gabsleo.meaudote.exceptions.FieldInUseException;
import com.gabsleo.meaudote.services.AppUserService;
import com.gabsleo.meaudote.services.AuthenticationService;
import com.gabsleo.meaudote.utils.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final AppUserService appUserService;

    @Autowired
    public AuthController(AuthenticationService authenticationService, AppUserService appUserService) {
        this.authenticationService = authenticationService;
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<String>> login(@RequestBody LoginDto request) throws AppUserNotFoundException {
        Response<String> response = new Response();
        JwtTokenDto authentication = authenticationService.authenticate(request);
        response.setData(authentication.token());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Response<String>> register(@Valid @RequestBody RegisterDto request) throws FieldInUseException {
        Response<String> response = new Response<>();
        response.setData(authenticationService.register(request).token());
        return ResponseEntity.ok(response);
    }
}
