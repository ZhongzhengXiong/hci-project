package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IntroPhotoNotFoundException extends RuntimeException{
    public IntroPhotoNotFoundException() {
        super("introduction photo of activity not found.");
    }

    public IntroPhotoNotFoundException(long activityId) {
        super(String.format("Could not find introduction photo of activity '%d'.", activityId));
    }
}
