package com.warehouse_accounting.util.exception;

public class NotFoundEntityException extends RuntimeException {

    public NotFoundEntityException(String message) {
        super(message);
    }

    public NotFoundEntityException(String message, Exception causeEx) {
        super(message, causeEx);
    }
}
