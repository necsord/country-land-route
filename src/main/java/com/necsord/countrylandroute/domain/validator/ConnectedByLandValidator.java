package com.necsord.countrylandroute.domain.validator;

import com.necsord.countrylandroute.domain.country.CountryDto;
import com.necsord.countrylandroute.domain.country.Region;

import java.util.Arrays;
import java.util.Collection;

public class ConnectedByLandValidator {
    /**
     * Europe, Asia and Africa share land path
     */
    static Collection<Region> EUROPE_ASIA_AFRICA = Arrays.asList(Region.EUROPE, Region.ASIA, Region.AFRICA);

    public boolean isValid(final CountryDto origin, CountryDto destination) {
        if (origin.region() == destination.region()) {
            return true;
        }

        return EUROPE_ASIA_AFRICA.contains(origin.region()) && EUROPE_ASIA_AFRICA.contains(destination.region());
    }
}
