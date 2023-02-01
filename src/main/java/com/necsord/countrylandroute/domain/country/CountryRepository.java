package com.necsord.countrylandroute.domain.country;

import java.util.Collection;
import java.util.Optional;

public interface CountryRepository {
    Optional<CountryDto> find(final String countryCode);

    Collection<String> getNeighbours(final String countryCode);
}
