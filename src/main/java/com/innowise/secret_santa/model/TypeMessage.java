package com.innowise.secret_santa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum TypeMessage {
    //TODO write all types messages
    INFO,
    CREATED("Hello %s, you register in secret santa successful"),
    CHANGE_PASSWORD("%s, your password changed successful");

    private String textMessage;

    TypeMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}