package com.gabsleo.meaudote.services;

import com.gabsleo.meaudote.dtos.AppUserDto;
import com.gabsleo.meaudote.dtos.PixKeyDto;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.entities.PixKey;
import com.gabsleo.meaudote.enums.Model;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.gabsleo.meaudote.repositories.PixKeyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PixKeyService {
    private final PixKeyRepository pixKeyRepository;

    @Autowired
    public PixKeyService(PixKeyRepository pixKeyRepository) {
        this.pixKeyRepository = pixKeyRepository;
    }

    public PixKey findById(UUID id) throws NotFoundException {
        return pixKeyRepository.findById(id).orElseThrow(() -> new NotFoundException(Model.PIX_KEY));
    }

    public PixKey findByCode(String code) throws NotFoundException {
        return pixKeyRepository.findByCode(code).orElseThrow(() -> new NotFoundException(Model.PIX_KEY));
    }

    public PixKey save(PixKey pixKey) {
        return pixKeyRepository.save(pixKey);
    }

    public PixKey update(PixKey pixKey, PixKeyDto pixKeyDto) {
        UUID id = pixKey.getId();
        BeanUtils.copyProperties(pixKeyDto, pixKey);
        pixKey.setId(id);
        return pixKeyRepository.save(pixKey);
    }

    public void delete(PixKey pixKey) {
        pixKeyRepository.delete(pixKey);
    }

    public List<PixKey> findByAppUser(AppUser appUser){
        return pixKeyRepository.findByAppUser(appUser);
    }

    public PixKey findByIdWhereAppUserEquals(UUID id, AppUser appUser) throws NotFoundException {
        return pixKeyRepository.findByIdWhereAppUserEquals(id, appUser.getEmail()).orElseThrow(() -> new NotFoundException(Model.PIX_KEY));
    }

    public PixKeyDto convertToDto(PixKey pixKey) {
        return new PixKeyDto(
                pixKey.getId(),
                pixKey.getCode(),
                pixKey.getType()
        );
    }
}
