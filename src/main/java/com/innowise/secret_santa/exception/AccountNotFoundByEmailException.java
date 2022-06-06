package com.innowise.secret_santa.exception;

public class AccountNotFoundByEmailException extends RuntimeException{
    public AccountNotFoundByEmailException(String message) {
        super(message);
    }
}
