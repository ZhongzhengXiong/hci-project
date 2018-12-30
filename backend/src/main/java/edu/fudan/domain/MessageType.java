package edu.fudan.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import edu.fudan.exception.IllegalActivityTypeException;

public enum MessageType {
    ALLUSER("ALLUSER"),
    PARTICIPATOR("PARTICIPATOR"),
    CREATOR("CREATOR");

    private String text;

    MessageType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    @JsonCreator
    public static MessageType fromText(String text) {
        for (MessageType t : MessageType.values()) {
            if(t.toString().equals(text)) {
                return t;
            }
        }
        throw new IllegalActivityTypeException();
    }
}
