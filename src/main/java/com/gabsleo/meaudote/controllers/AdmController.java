package com.gabsleo.meaudote.controllers;

import com.gabsleo.meaudote.dtos.AppUserDto;
import com.gabsleo.meaudote.dtos.admin.AdminAppRoleDto;
import com.gabsleo.meaudote.dtos.admin.AdminAppUserDto;
import com.gabsleo.meaudote.entities.AppRole;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.exceptions.FieldInUseException;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.services.AdoptionAnimalService;
import com.gabsleo.meaudote.services.AppRoleService;
import com.gabsleo.meaudote.services.AppUserService;
import com.gabsleo.meaudote.services.PixKeyService;
import com.gabsleo.meaudote.utils.Response;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/adm")
@CrossOrigin("*")
public class AdmController {
    private final AppUserService appUserService;
    private final AppRoleService appRoleService;
    private final AdoptionAnimalService adoptionAnimalService;
    private final PixKeyService pixKeyService;

    @Autowired
    public AdmController(AppUserService appUserService, AppRoleService appRoleService, AdoptionAnimalService adoptionAnimalService, PixKeyService pixKeyService) {
        this.appUserService = appUserService;
        this.appRoleService = appRoleService;
        this.adoptionAnimalService = adoptionAnimalService;
        this.pixKeyService = pixKeyService;
    }

    @GetMapping("/users")
    public ResponseEntity<Response<Page<AppUserDto>>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "size", defaultValue = "20") int objectsPerPage
    ) {
        Response<Page<AppUserDto>> response = new Response<>();
        Pageable request = PageRequest.of(page, objectsPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<AppUser> userPage = appUserService.findAll(request);
        Page<AppUserDto> userPageDto = userPage.map(appUserService::convertToDto);
        response.setData(userPageDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{cpf}")
    public ResponseEntity<Response<AdminAppUserDto>> editUser(@PathParam("cpf") String cpf) throws NotFoundException {
        Response<AdminAppUserDto> response = new Response<>();
        AppUser appUser = appUserService.findByCpf(cpf);
        AdminAppUserDto adminAppUserDto = appUserService.convertToAdminDto(appUser);
        response.setData(adminAppUserDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/roles")
    public ResponseEntity<Response<List<AdminAppRoleDto>>> getAll() {
        Response<List<AdminAppRoleDto>> response = new Response<>();
        response.setData(appRoleService.findAll().stream().map(appRoleService::convertToDto).toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/roles")
    public ResponseEntity<Response<AdminAppRoleDto>> saveRole(@RequestBody AdminAppRoleDto appRoleDto) throws FieldInUseException {
        Response<AdminAppRoleDto> response = new Response<>();
        AppRole appRole = new AppRole();
        BeanUtils.copyProperties(appRoleDto, appRole);
        response.setData(appRoleService.convertToDto(appRoleService.save(appRole)));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/roles/{name}")
    public ResponseEntity<Response<AdminAppRoleDto>> saveRole(@PathParam("name") String pathParam, @RequestBody AdminAppRoleDto appRoleDto) throws FieldInUseException, NotFoundException {
        Response<AdminAppRoleDto> response = new Response<>();
        AppRole appRole = appRoleService.findByName(pathParam);
        BeanUtils.copyProperties(appRoleDto, appRole);
        response.setData(appRoleService.convertToDto(appRoleService.save(appRole)));
        return ResponseEntity.ok(response);
    }
}
