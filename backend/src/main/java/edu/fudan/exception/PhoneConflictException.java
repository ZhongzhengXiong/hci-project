package edu.fudan.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PhoneConflictException extends RuntimeException {

    public PhoneConflictException() {
        super("This email has already existed.");
    }

    public PhoneConflictException(String email) {
        super(String.format("Email '%s' has already existed.", email));
    }
}
