package com.innowise.secret_santa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum TypeMessage {
    INFO,
    CREATE("Hello %s, you register in secret santa successful"),
    CHANGE_PASSWORD("%s, your password changed successful"),
    DELETE("Hello %s, our account removed in secret santa");

    private String textMessage;

    TypeMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}