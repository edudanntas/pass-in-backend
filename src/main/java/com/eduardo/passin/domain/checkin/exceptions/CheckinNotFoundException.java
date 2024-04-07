package com.eduardo.passin.domain.checkin.exceptions;

public class CheckinNotFoundException extends RuntimeException{
    public CheckinNotFoundException(String message) {
        super(message);
    }
}
