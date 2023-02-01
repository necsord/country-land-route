package com.necsord.countrylandroute.domain.country;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
class CountryClient {
    WebClient webClient;

    CountryClientProperties properties;

    static ParameterizedTypeReference<Collection<CountryDto>> TYPE_REFERENCE = new ParameterizedTypeReference<>(){};

    @Cacheable("countries")
    public Map<String, CountryDto> fetch() {
        return Objects.requireNonNull(webClient.get()
                .uri(properties.getBaseUrl() + properties.getCountriesPath())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TYPE_REFERENCE)
                .block())
            .stream()
            .collect(Collectors.toMap(CountryDto::cca3, o -> o, (prev, next) -> next, HashMap::new));
    }
}
