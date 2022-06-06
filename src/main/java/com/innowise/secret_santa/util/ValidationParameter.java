package com.innowise.secret_santa.util;

import com.innowise.secret_santa.exception.EmailInvalidException;
import com.innowise.secret_santa.exception.IncorrectPasswordException;
import com.innowise.secret_santa.exception.NotParameterException;

import java.util.regex.Pattern;

public final class ValidationParameter {

    private static final String REG_EMAIL;

    static {
        REG_EMAIL = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    }

    private ValidationParameter() {
    }

    public static void checkParameterIsEmpty(String... value) {
        for (String s : value) {
            if (s.isBlank()) {
                throw new NotParameterException("Login or password is empty");
            }
        }
    }

    public static void checkEmail(String email) {
        if (!Pattern.matches(REG_EMAIL, email)) {
            throw new EmailInvalidException("Email " + email + " invalid!!!!");
        }
    }

    public static void checkPassword(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new IncorrectPasswordException("Passwords do not match");
        }
    }
}
