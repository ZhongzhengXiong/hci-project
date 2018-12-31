package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAvatarNotExistException extends RuntimeException{
    public UserAvatarNotExistException() {
        super("User doesn't have avatar.");
    }

    public UserAvatarNotExistException(long userId) {
        super(String.format("user '%d' doesn't have avatar.", userId));
    }
}
