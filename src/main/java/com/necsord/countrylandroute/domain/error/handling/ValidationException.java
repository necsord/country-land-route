package com.necsord.countrylandroute.domain.error.handling;

public class ValidationException extends RuntimeException {
    public ValidationException(final String message) {
        super(message);
    }
}
