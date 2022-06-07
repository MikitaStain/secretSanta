package com.innowise.secret_santa.service;

public interface EmailService {

    void sendMail(String to, String subject, String text);
}
