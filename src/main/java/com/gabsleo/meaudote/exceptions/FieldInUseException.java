package com.gabsleo.meaudote.exceptions;

import com.gabsleo.meaudote.enums.UniqueField;

public class FieldInUseException extends Exception {
    public FieldInUseException(UniqueField field) {
        super(field.getName() + " already in use.");
    }
}
