package com.gabsleo.meaudote.services;
import com.gabsleo.meaudote.dtos.AppUserDto;
import com.gabsleo.meaudote.dtos.EmailDto;
import com.gabsleo.meaudote.dtos.NameDto;
import com.gabsleo.meaudote.dtos.UpdateAppUserDto;
import com.gabsleo.meaudote.dtos.admin.AdminAppUserDto;
import com.gabsleo.meaudote.entities.AppRole;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.enums.UniqueField;
import com.gabsleo.meaudote.exceptions.AppUserNotLoggedException;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.exceptions.FieldInUseException;
import com.gabsleo.meaudote.repositories.AdoptionAnimalRepository;
import com.gabsleo.meaudote.repositories.AppUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppRoleService appRoleService;
    private final AdoptionAnimalRepository adoptionAnimalRepository;
    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, AppRoleService appRoleService, AdoptionAnimalRepository adoptionAnimalRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.appRoleService = appRoleService;
        this.adoptionAnimalRepository = adoptionAnimalRepository;
    }
    public AppUser save(AppUser appUser){
        return appUserRepository.save(appUser);
    }
    public AppUser register(AppUser appUser) throws FieldInUseException, NotFoundException {
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
    public AppUser update(AppUser appUser, UpdateAppUserDto appUserDto){
        String cpf = appUser.getCpf();
        BeanUtils.copyProperties(appUserDto, appUser);
        System.out.println(appUser.toString());
        appUser.setCpf(cpf);
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

    public Page<AppUser> findAll(Pageable pageable){
        return appUserRepository.findAll(pageable);
    }

    public AppUser findByCpf(String cpf) throws NotFoundException {
        return appUserRepository.findByCpf(cpf).orElseThrow(NotFoundException::new);
    }
    public AppUser findByEmail(String email) throws NotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    public AppUser findByEmail(Principal principal) throws NotFoundException, AppUserNotLoggedException {
        if(principal == null){
            throw new AppUserNotLoggedException();
        }
        return appUserRepository.findByEmail(principal.getName()).orElseThrow(NotFoundException::new);
    }

    public AppUser findByName(String username) throws NotFoundException {
        return appUserRepository.findByName(username).orElseThrow(NotFoundException::new);
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

    public AppUserDto convertToDto(AppUser appUser){
        return new AppUserDto(
                appUser.getName(),
                appUser.getPhone(),
                appUser.getProfilePicture(),
                appUser.getBannerPicture(),
                appUser.getState(),
                appUser.getCity(),
                adoptionAnimalRepository.findPetAmountWhereAppUserNameEquals(appUser.getName())
        );
    }

    public AdminAppUserDto convertToAdminDto(AppUser appUser){
        return new AdminAppUserDto(
            appUser.getCpf(),
            appUser.getName(),
            appUser.getEmail(),
            appUser.getCreatedAt(),
            appUser.getPhone(),
            appUser.getProfilePicture(),
            appUser.getBannerPicture(),
            appUser.getNGO(),
            appUser.getState(),
            appUser.getCity(),
            appUser.getAppRoles().stream().map(AppRole::getName).toList()
        );
    }
}
