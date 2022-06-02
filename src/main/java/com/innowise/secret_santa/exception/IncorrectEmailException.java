package com.innowise.secret_santa.exception;

public class IncorrectEmailException extends RuntimeException{
    public IncorrectEmailException(String message) {
        super(message);
    }
}