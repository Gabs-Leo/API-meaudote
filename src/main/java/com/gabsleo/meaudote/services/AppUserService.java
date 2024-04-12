package com.gabsleo.meaudote.services;

import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser save(AppUser appUser){
        return appUserRepository.save(appUser);
    }
}
