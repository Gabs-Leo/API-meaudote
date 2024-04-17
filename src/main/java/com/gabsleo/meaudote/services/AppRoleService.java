package com.gabsleo.meaudote.services;

import com.gabsleo.meaudote.entities.AppRole;
import com.gabsleo.meaudote.enums.Model;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.repositories.AppRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppRoleService {
    private final AppRoleRepository appRoleRepository;

    @Autowired
    public AppRoleService(AppRoleRepository appRoleRepository) {
        this.appRoleRepository = appRoleRepository;
    }

    public AppRole save(AppRole appRole){
        return appRoleRepository.save(appRole);
    }
    public AppRole findByName(String name) throws NotFoundException {
        return appRoleRepository.findByName(name).orElseThrow(() -> new NotFoundException(Model.APP_ROLE));
    }
}
