package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PhoneOrPasswordException extends RuntimeException {

    public PhoneOrPasswordException() {
        super("Email is invalid or password is wrong.");
    }
}
