package com.annette.spring.project.online_store.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(
        UserBadAuthoritiesException exception) {

        UserIncorrectData data = new UserIncorrectData();
        
        data.setInfo(exception.getMessage());

        return new ResponseEntity<UserIncorrectData>(data, 
            HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(
        AuthorizationDeniedException exception) {

        UserIncorrectData data = new UserIncorrectData();
        
        data.setInfo(exception.getMessage());

        return new ResponseEntity<UserIncorrectData>(data, 
            HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(
        Exception exception) {

        UserIncorrectData data = new UserIncorrectData();
        
        exception.printStackTrace();
        
        data.setInfo(exception.getMessage());

        return new ResponseEntity<UserIncorrectData>(data, 
            HttpStatus.BAD_REQUEST);

    }

}
