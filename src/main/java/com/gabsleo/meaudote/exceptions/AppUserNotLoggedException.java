package com.gabsleo.meaudote.exceptions;

public class AppUserNotLoggedException extends Exception{
    public AppUserNotLoggedException() {
        super("User not logged.");
    }

    public AppUserNotLoggedException(String message) {
        super(message);
    }
}
