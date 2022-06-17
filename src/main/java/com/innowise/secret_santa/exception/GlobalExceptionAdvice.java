package com.innowise.secret_santa.exception;

import com.innowise.secret_santa.util.CalendarUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorObject> handleNoDataFoundException(NoDataFoundException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SaveDataException.class)
    public ResponseEntity<ErrorObject> handleAccountCreatedException(SaveDataException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnknownMessageException.class)
    public ResponseEntity<ErrorObject> handleUnknownMessageException(UnknownMessageException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorSendEmailException.class)
    public ResponseEntity<ErrorObject> handleErrorSendEmailException(ErrorSendEmailException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MapperException.class)
    public ResponseEntity<ErrorObject> handleMapperException(MapperException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<ErrorObject> handleIncorrectDataException(IncorrectDataException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<ErrorObject> handleNoAccessException(NoAccessException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.FORBIDDEN);
    }
}