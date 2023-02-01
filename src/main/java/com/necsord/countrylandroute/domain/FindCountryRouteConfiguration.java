package com.necsord.countrylandroute.domain;

import com.necsord.countrylandroute.domain.country.CountryClientConfiguration;
import com.necsord.countrylandroute.domain.country.CountryRepository;
import com.necsord.countrylandroute.domain.validator.ConnectedByLandValidator;
import com.necsord.countrylandroute.domain.validator.ValidatorConfiguration;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Import({CountryClientConfiguration.class, ValidatorConfiguration.class})
@Configuration
public class FindCountryRouteConfiguration {

    @Bean
    public FindCountryRouteUC findCountryRouteUC(
        final CountryRepository countryRepository,
        final ConnectedByLandValidator connectedByLandValidator
    ) {
        return new FindCountryRouteUC(countryRepository, connectedByLandValidator);
    }
}
