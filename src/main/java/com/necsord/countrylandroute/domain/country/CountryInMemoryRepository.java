package com.necsord.countrylandroute.domain.country;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
@AllArgsConstructor
public class CountryInMemoryRepository implements CountryRepository {
    CountryClient client;

    @Override
    public Optional<CountryDto> find(final String countryCode) {
        return Optional.ofNullable(client.fetch().get(countryCode));
    }

    @Override
    public Collection<String> getNeighbours(final String countryCode) {
        return find(countryCode)
            .map(CountryDto::borders)
            .orElse(List.of());
    }
}
