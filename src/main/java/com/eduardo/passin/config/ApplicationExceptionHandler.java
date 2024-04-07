package com.eduardo.passin.config;

import com.eduardo.passin.domain.attendee.exception.AttendeeAlreadyRegisteredException;
import com.eduardo.passin.domain.attendee.exception.AttendeeNotFoundException;
import com.eduardo.passin.domain.checkin.exceptions.CheckInAlreadyExistException;
import com.eduardo.passin.domain.checkin.exceptions.CheckinNotFoundException;
import com.eduardo.passin.domain.event.exceptions.EventFullException;
import com.eduardo.passin.domain.event.exceptions.EventNotFoundException;
import com.eduardo.passin.dto.exception.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<Void> handleEventNotFoundException(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity<Void> handleAttendeeNotFoundException(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(CheckinNotFoundException.class)
    public ResponseEntity<Void> handleCheckinNotFoundException(CheckinNotFoundException exception){
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ExceptionDTO> handleEventFullException(EventFullException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(AttendeeAlreadyRegisteredException.class)
    public ResponseEntity<Void> handleAttendeeAlreadyRegisteredException(AttendeeAlreadyRegisteredException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistException.class)
    public ResponseEntity<Void> handleCheckInAlreadyExistException(CheckInAlreadyExistException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
