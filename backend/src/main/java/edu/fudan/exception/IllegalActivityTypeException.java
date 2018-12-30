package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalActivityTypeException extends RuntimeException {
    public IllegalActivityTypeException() {
        super("Illegal activity type.");
    }

    public IllegalActivityTypeException(String input) {
        super(String.format("%s is an illegal activity type", input));
    }
}
