package com.epam.kindergartermenumaker;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/17/2020
 **/
public enum ConstraintViolationExceptionMessage {

    NOT_NULL("must not be null"),
    GREATER_THEN_ZERO("must be greater than or equal to 1");

    private final String message;

    ConstraintViolationExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
