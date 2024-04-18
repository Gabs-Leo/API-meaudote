package com.gabsleo.meaudote.exceptions;

import com.gabsleo.meaudote.enums.Model;

public class NotFoundException extends Exception{
    public NotFoundException() {
        super("User not found.");
    }
    public NotFoundException(Model model) {
        super(model.getName()+" not found.");
    }
    public NotFoundException(String message) {
        super(message);
    }
}
