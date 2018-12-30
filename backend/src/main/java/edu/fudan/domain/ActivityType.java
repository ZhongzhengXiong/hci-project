package edu.fudan.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import edu.fudan.exception.IllegalActivityTypeException;

public enum ActivityType {
    PARTY("PARTY"),
    LECTURE("LECTURE");

    private String text;

    ActivityType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    @JsonCreator
    public static ActivityType fromText(String text) {
        for (ActivityType t : ActivityType.values()) {
            if(t.toString().equals(text)) {
                return t;
            }
        }
        throw new IllegalActivityTypeException();
    }

}
