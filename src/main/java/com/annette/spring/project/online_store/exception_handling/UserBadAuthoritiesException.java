package com.annette.spring.project.online_store.exception_handling;

public class UserBadAuthoritiesException extends RuntimeException {

    public UserBadAuthoritiesException(String message) {
        super(message);
    }

}
