package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "IO Exception")
public class ImageIOException extends RuntimeException{
    public ImageIOException(){
        super("Image IO Exception");
    }

    public ImageIOException(String message){
        super(message);
    }
}
