package com.gabsleo.meaudote.enums;

public enum UniqueField {
    EMAIL("Email"),
    CPF ("CPF"),
    USERNAME("Username"),
    APP_ROLE_NAME("Role name");

    private String name;
    UniqueField(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
