package com.necsord.countrylandroute.domain;

import com.necsord.countrylandroute.domain.breadthfirstsearch.BreadthFirstSearch;
import com.necsord.countrylandroute.domain.country.CountryRepository;
import com.necsord.countrylandroute.domain.error.handling.NoLandCrossingException;
import com.necsord.countrylandroute.domain.error.handling.ValidationException;
import com.necsord.countrylandroute.domain.validator.ConnectedByLandValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.util.Collection;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FindCountryRouteUC {
    CountryRepository countryRepository;
    ConnectedByLandValidator connectedByLandValidator;

    /**
     * Method finds path between origin and destination country.
     *
     * @param origin      Country ISO3166 Alpha-3 code
     * @param destination Country ISO3166 Alpha-3 code
     * @return Countries ISO3166 Alpha-3 path codes from origin to destination
     * @throws ValidationException     When origin or destination country code is not supported
     * @throws NoLandCrossingException When there's no land crossing possible due to oceans
     */
    public Collection<String> find(final String origin, final String destination) {
        val originCountry = countryRepository.find(origin)
            .orElseThrow(() -> new ValidationException("Unsupported country: " + origin));
        val destinationCountry = countryRepository.find(destination)
            .orElseThrow(() -> new ValidationException("Unsupported country: " + destination));

        if (!connectedByLandValidator.isValid(originCountry, destinationCountry)) {
            throw new NoLandCrossingException("There's no land crossing");
        }

        return BreadthFirstSearch.search(origin, destination, countryRepository::getNeighbours)
            .orElseThrow(() -> new NoLandCrossingException("There's no land crossing"));
    }
}
