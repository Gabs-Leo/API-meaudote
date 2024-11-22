package com.gabsleo.meaudote.enums;

public enum ImageExtension {
    JPG("jpg"), PNG("png"), JPEG("jpeg"), WEBP("webp"), GIF("gif");

    private final String extension;
    ImageExtension(String extension) {
        this.extension = extension;
    }
    public String getExtension() {
        return extension;
    }
}
