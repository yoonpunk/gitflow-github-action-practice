package com.practice.git.service.exception;

public class ResourceNotExistException extends RuntimeException {

    public static final String MEMBER_NOT_EXIST = "MEMBER_NOT_EXIST";

    public ResourceNotExistException(String message) {
        super(message);
    }
}
