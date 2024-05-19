package com.gabsleo.meaudote.dtos;

import java.util.UUID;

public record AppUserDto(String name, String phone, String profilePicture, String bannerPicture, String state, String city, Integer petAmount) {
}
