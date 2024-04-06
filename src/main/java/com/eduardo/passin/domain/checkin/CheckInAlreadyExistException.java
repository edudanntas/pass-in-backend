package com.eduardo.passin.domain.checkin;

public class CheckInAlreadyExistException extends RuntimeException{
    public CheckInAlreadyExistException(String message) {
        super(message);
    }
}
