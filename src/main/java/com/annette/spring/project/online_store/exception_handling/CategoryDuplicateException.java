package com.annette.spring.project.online_store.exception_handling;

public class CategoryDuplicateException extends RuntimeException {

    public CategoryDuplicateException(String message) {
        super(message);
    }

}
