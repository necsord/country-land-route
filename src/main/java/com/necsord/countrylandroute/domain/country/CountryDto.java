package com.necsord.countrylandroute.domain.country;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CountryDto(String cca3, Region region, List<String> borders) {
}
