package com.annette.spring.project.online_store.exception_handling;

public class NoSuchProductException extends RuntimeException {

    public NoSuchProductException(String message) {
        super(message);
    }

}
