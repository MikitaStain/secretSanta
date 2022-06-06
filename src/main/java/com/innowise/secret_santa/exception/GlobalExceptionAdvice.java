package com.innowise.secret_santa.exception;

import com.innowise.secret_santa.util.CalendarUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(EmailUsedException.class)
    public ResponseEntity<ErrorObject> handleEmailUsedException(EmailUsedException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotElementByIdException.class)
    public ResponseEntity<ErrorObject> handleNotElementByIdException(NotElementByIdException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectEmailException.class)
    public ResponseEntity<ErrorObject> handleIncorrectEmailException(IncorrectEmailException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailInvalidException.class)
    public ResponseEntity<ErrorObject> handleEmailInvalidException(EmailInvalidException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotParameterException.class)
    public ResponseEntity<ErrorObject> handleNotParameterException(NotParameterException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundByEmailException.class)
    public ResponseEntity<ErrorObject> handleAccountNotFoundByEmailException(AccountNotFoundByEmailException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorObject> handleIncorrectPasswordException(IncorrectPasswordException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorObject> handleNoDataFoundException(NoDataFoundException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }
}