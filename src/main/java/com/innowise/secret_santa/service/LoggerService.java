package com.innowise.secret_santa.service;

public interface LoggerService<T> {

    void loggerInfo(String message, Object ... objects);

    T logger(String message, T objects);


}
