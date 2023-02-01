package com.necsord.countrylandroute.domain.validator;

import org.springframework.context.annotation.Bean;

public class ValidatorConfiguration {
    @Bean
    public ConnectedByLandValidator connectedByLandValidator() {
        return new ConnectedByLandValidator();
    }
}
