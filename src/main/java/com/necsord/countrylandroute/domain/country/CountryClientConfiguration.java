package com.necsord.countrylandroute.domain.country;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.val;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(CountryClientProperties.class)
@EnableCaching
public class CountryClientConfiguration {
    public static final int MAX_IN_MEMORY_SIZE_TWO_MB = 2 * 1024 * 1024;

    @Bean
    CountryRepository countryRepository(final CountryClient countryClient) {
        return new CountryInMemoryRepository(countryClient);
    }

    @Bean
    CountryClient countryClient(final WebClient webClient, final CountryClientProperties properties) {
        return new CountryClient(webClient, properties);
    }

    @Bean
    WebClient webClient() {
        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs(this::registerCustomCodecs)
            .build();

        return WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .build();
    }

    private void registerCustomCodecs(final ClientCodecConfigurer clientCodecConfigurer) {
        val objectMapper = JsonMapper.builder()
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .build();
        val decoder = new Jackson2JsonDecoder(objectMapper, MediaType.TEXT_PLAIN);
        decoder.setMaxInMemorySize(MAX_IN_MEMORY_SIZE_TWO_MB);

        clientCodecConfigurer.customCodecs()
            .register(decoder);
    }
}
