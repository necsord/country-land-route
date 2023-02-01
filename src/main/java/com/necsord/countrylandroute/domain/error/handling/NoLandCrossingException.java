package com.necsord.countrylandroute.domain.error.handling;

public class NoLandCrossingException extends RuntimeException {
    public NoLandCrossingException(final String message) {
        super(message);
    }
}
