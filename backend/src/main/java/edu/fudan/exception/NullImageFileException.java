package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullImageFileException extends RuntimeException{
    public NullImageFileException() {
        super("Image File is Null");
    }
}
