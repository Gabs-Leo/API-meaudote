package com.gabsleo.meaudote.services;
import com.gabsleo.meaudote.dtos.EmailDto;
import com.gabsleo.meaudote.dtos.NameDto;
import com.gabsleo.meaudote.dtos.UpdateAppUserDto;
import com.gabsleo.meaudote.entities.AppRole;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.enums.UniqueField;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.exceptions.FieldInUseException;
import com.gabsleo.meaudote.repositories.AppUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppRoleService appRoleService;
    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, AppRoleService appRoleService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.appRoleService = appRoleService;
    }
    public AppUser save(AppUser appUser){
        return appUserRepository.save(appUser);
    }
    public AppUser register(AppUser appUser) throws FieldInUseException {
        if(appUserRepository.findByEmail(appUser.getEmail()).isPresent()){
            throw new FieldInUseException(UniqueField.EMAIL);
        }else if(appUserRepository.findByCpf(appUser.getCpf()).isPresent()){
            throw new FieldInUseException(UniqueField.CPF);
        }else if(appUserRepository.findByName(appUser.getName()).isPresent()){
            throw new FieldInUseException(UniqueField.USERNAME);
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        AppUser user = appUserRepository.save(appUser);
        this.attachRole(user, appRoleService.findByName("USER"));
        return user;
    }
    public AppUser udpate(AppUser appUser, UpdateAppUserDto appUserDto){
        BeanUtils.copyProperties(appUserDto, appUser);
        return this.save(appUser);
    }

    public AppUser udpateEmail(AppUser appUser, EmailDto emailDto) throws FieldInUseException {
        if(appUserRepository.findByEmail(appUser.getEmail()).isPresent()){
            throw new FieldInUseException(UniqueField.EMAIL);
        }
        appUser.setEmail(emailDto.email());
        return this.save(appUser);
    }

    public AppUser updateName(AppUser appUser, NameDto nameDto) throws FieldInUseException {
        if(appUserRepository.findByName(appUser.getName()).isPresent()){
            throw new FieldInUseException(UniqueField.USERNAME);
        }
        appUser.setName(nameDto.name());
        return this.save(appUser);
    }

    public AppUser findByCpf(String cpf) throws NotFoundException {
        return appUserRepository.findByCpf(cpf).orElseThrow(NotFoundException::new);
    }
    public AppUser findByEmail(String email) throws NotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(appUserRepository.findByEmail(email).isEmpty())
            throw new UsernameNotFoundException("User not found.");

        AppUser appUser = appUserRepository.findByEmail(email).get();
        Collection<SimpleGrantedAuthority> authors = new ArrayList<>();
        appUser.getAppRoles().forEach(i ->
                authors.add(new SimpleGrantedAuthority(i.getName()))
        );

        return new User(appUser.getEmail(), appUser.getPassword(), authors);
    }

    public void attachRole(AppUser appUser, AppRole appRole){
        appUser.getAppRoles().add(appRole);
        this.save(appUser);
    }
}
