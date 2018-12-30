package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalMessageTypeException extends  RuntimeException{

    public IllegalMessageTypeException() {
        super("Illegal message type.");
    }

    public IllegalMessageTypeException(String input) {
        super(String.format("%s is an illegal message type", input));
    }
}
