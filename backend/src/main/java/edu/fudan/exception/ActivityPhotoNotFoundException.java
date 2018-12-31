package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ActivityPhotoNotFoundException extends RuntimeException {
    public ActivityPhotoNotFoundException() {
        super("photo not found.");
    }

    public ActivityPhotoNotFoundException(long photoId) {
        super(String.format("Could not find photo '%d'.", photoId));
    }
}

