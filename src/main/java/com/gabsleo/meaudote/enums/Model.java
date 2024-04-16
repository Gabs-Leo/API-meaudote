package com.gabsleo.meaudote.enums;

public enum Model {
    APP_USER("User"),
    ADOPTION_ANIMAL("Adoption Animal");
    private final String name;
    Model(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
