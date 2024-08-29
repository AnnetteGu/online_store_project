package com.annette.spring.project.online_store.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(
        UserBadAuthoritiesException exception) {

        IncorrectData data = new IncorrectData();
        
        data.setInfo(exception.getMessage());

        return new ResponseEntity<IncorrectData>(data, 
            HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(
        AuthorizationDeniedException exception) {

        IncorrectData data = new IncorrectData();
        
        data.setInfo(exception.getMessage());

        return new ResponseEntity<IncorrectData>(data, 
            HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(
        NoSuchProductException exception) {

        IncorrectData data = new IncorrectData();
        
        data.setInfo(exception.getMessage());

        return new ResponseEntity<IncorrectData>(data, 
            HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(
        CategoryDuplicateException exception) {

        IncorrectData data = new IncorrectData();

        data.setInfo(exception.getMessage());

        return new ResponseEntity<IncorrectData>(data, 
            HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(
        Exception exception) {

        IncorrectData data = new IncorrectData();
        
        exception.printStackTrace();
        
        data.setInfo(exception.getMessage());

        return new ResponseEntity<IncorrectData>(data, 
            HttpStatus.BAD_REQUEST);

    }

}
