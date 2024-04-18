package com.gabsleo.meaudote.services;

import com.gabsleo.meaudote.dtos.JwtTokenDto;
import com.gabsleo.meaudote.dtos.LoginDto;
import com.gabsleo.meaudote.dtos.RegisterDto;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.exceptions.FieldInUseException;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(AppUserService appUserService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public JwtTokenDto register(RegisterDto request) throws FieldInUseException, NotFoundException {
        AppUser user = new AppUser();
        BeanUtils.copyProperties(request, user);
        appUserService.register(user);

        String token = jwtService.generateToken(user);
        return new JwtTokenDto(token);
    }

    public JwtTokenDto authenticate(LoginDto request) throws NotFoundException, AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));
        AppUser user = appUserService.findByEmail(request.email());
        String token = jwtService.generateToken(user);
        return new JwtTokenDto(token);
    }


}
