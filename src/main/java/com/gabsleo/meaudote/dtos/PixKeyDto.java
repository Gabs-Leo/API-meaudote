package com.gabsleo.meaudote.dtos;

import com.gabsleo.meaudote.enums.PixKeyType;

import java.util.UUID;

public record PixKeyDto(UUID id, String code, PixKeyType type) {  }
