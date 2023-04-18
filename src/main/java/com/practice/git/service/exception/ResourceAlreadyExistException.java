package com.practice.git.service.exception;

public class ResourceAlreadyExistException extends RuntimeException {

    public static final String MEMBER_ALREADY_EXIST = "MEMBER_ALREADY_EXIST";

    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
