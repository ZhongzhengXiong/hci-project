package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongFileTypeException extends RuntimeException{
    public WrongFileTypeException() {
        super("The File uploaded isn't an image file");
    }
}
