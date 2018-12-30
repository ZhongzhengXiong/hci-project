package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException() {
        super("Message not found.");
    }

    public MessageNotFoundException(long messageId) {
        super(String.format("Could not find message '%d'.", messageId));
    }
}
