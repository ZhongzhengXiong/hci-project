package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ActivityNotFoundException extends  RuntimeException{
    public ActivityNotFoundException() {
        super("Activity not found.");
    }

    public ActivityNotFoundException(long activityId) {
        super(String.format("Could not find activity '%d'.", activityId));
    }
}
