package com.necsord.countrylandroute.domain.country;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ConfigurationProperties(prefix = "necsord.country")
public class CountryClientProperties {
    @Setter
    String baseUrl;
    final String countriesPath;

}
