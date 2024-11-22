package com.gabsleo.meaudote.exceptions;

public class ImageOversizedException extends Exception {
    public ImageOversizedException(int width, int height) {
        super("Image size of "+width+"x"+height+" is higher than the max resolution of 1024x1024");
    }
}
