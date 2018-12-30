package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidInvitingcodeException extends RuntimeException{
    public InvalidInvitingcodeException() {
        super("inviting code invalid");
    }
}
