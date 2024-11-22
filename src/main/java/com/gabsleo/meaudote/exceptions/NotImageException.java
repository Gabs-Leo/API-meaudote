package com.gabsleo.meaudote.exceptions;

import com.gabsleo.meaudote.enums.ImageExtension;

import java.util.Arrays;

public class NotImageException extends RuntimeException {
    public NotImageException(String extension) {
        super(extension + " file is not allowed, the allowed extensions are " + Arrays.toString(ImageExtension.values()));
    }
}
