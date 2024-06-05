package com.gabsleo.meaudote.filters;

import com.gabsleo.meaudote.enums.Species;

import java.util.List;

public record AdoptionAnimalFilter (Integer maxAge, List<Species> species, String state, String city){
}
