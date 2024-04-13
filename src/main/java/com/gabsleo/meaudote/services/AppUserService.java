package com.gabsleo.meaudote.services;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.exceptions.AppUserNotFoundException;
import com.gabsleo.meaudote.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public AppUser save(AppUser appUser){
        return appUserRepository.save(appUser);
    }
    public AppUser register(AppUser appUser){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }
    public AppUser findByCpf(String cpf) throws AppUserNotFoundException {
        return appUserRepository.findByCpf(cpf).orElseThrow(AppUserNotFoundException::new);
    }
    public AppUser findByEmail(String email) throws AppUserNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(AppUserNotFoundException::new);
    }
}
