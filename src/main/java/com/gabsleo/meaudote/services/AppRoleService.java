package com.gabsleo.meaudote.services;

import com.gabsleo.meaudote.dtos.admin.AdminAppRoleDto;
import com.gabsleo.meaudote.entities.AppRole;
import com.gabsleo.meaudote.enums.Model;
import com.gabsleo.meaudote.enums.UniqueField;
import com.gabsleo.meaudote.exceptions.FieldInUseException;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.repositories.AppRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppRoleService {
    private final AppRoleRepository appRoleRepository;

    @Autowired
    public AppRoleService(AppRoleRepository appRoleRepository) {
        this.appRoleRepository = appRoleRepository;
    }
    public AppRole save(AppRole appRole) throws FieldInUseException {
        if(appRoleRepository.findByName(appRole.getName()).isEmpty()){
            return appRoleRepository.save(appRole);
        }
        throw new FieldInUseException(UniqueField.APP_ROLE_NAME);
    }
    public AppRole findByName(String name) throws NotFoundException {
        return appRoleRepository.findByName(name).orElseThrow(() -> new NotFoundException(Model.APP_ROLE));
    }
    public List<AppRole> findAll(){
        return appRoleRepository.findAll();
    }
    public AdminAppRoleDto convertToDto(AppRole appRole){
        return new AdminAppRoleDto(appRole.getName());
    }
}
