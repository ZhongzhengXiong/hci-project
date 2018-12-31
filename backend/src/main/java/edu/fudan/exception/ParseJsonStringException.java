package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParseJsonStringException extends RuntimeException{
    public ParseJsonStringException() {
        super("throw exception when parsing json string.");
    }
}
