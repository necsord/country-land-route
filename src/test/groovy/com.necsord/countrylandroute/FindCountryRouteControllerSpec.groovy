package com.necsord.countrylandroute

import com.necsord.countrylandroute.api.FindCountryRouteController
import com.necsord.countrylandroute.domain.FindCountryRouteConfiguration
import com.necsord.countrylandroute.domain.country.CountryClientProperties
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.jetbrains.annotations.NotNull
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@WebMvcTest(FindCountryRouteController.class)
@Import(FindCountryRouteConfiguration.class)
@ExtendWith(MockitoExtension.class)
class FindCountryRouteControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc
    @Autowired
    CountryClientProperties properties

    MockWebServer server = new MockWebServer()

    void setup() {
        def dispatcher = new Dispatcher() {
            @Override
            MockResponse dispatch(@NotNull final RecordedRequest recordedRequest) throws InterruptedException {
                if (recordedRequest.getPath() == "/mledoze/countries/master/countries.json") {
                    return new MockResponse()
                        .addHeader("Content-Type", "text/plain")
                        .setBody(new File("src/test/resources/countries.json").getText())
                }
                return new MockResponse().setResponseCode(404)
            }
        }
        server.setDispatcher(dispatcher)
        server.start()

        properties.setBaseUrl(server.url("/mledoze/countries/master").toString())
    }

    void cleanup() {
        server.shutdown()
    }

    def "When requested route from an invalid country, it should return bad request response with message"() {
        when:
            def resultActions = makeRequest(origin, destination)
        then:
            resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"message\":\"Unsupported country: invalid country\"}"))
        where:
            origin            | destination
            "invalid country" | "POL"
            "POL"             | "invalid country"
    }

    def "When requested route with valid data, it should return expected route"() {
        when:
            def resultActions = makeRequest(origin, destination)
        then:
            resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedResponse))
        where:
            origin | destination | expectedResponse
            "POL"  | "POL"       | "{\"route\":[\"POL\"]}"
            "POL"  | "ESP"       | "{\"route\":[\"POL\",\"DEU\",\"FRA\",\"ESP\"]}"
            "GRC"  | "EGY"       | "{\"route\":[\"GRC\",\"TUR\",\"SYR\",\"ISR\",\"EGY\"]}"
    }

    def "When requested route between countries from different, not connected continents, it should return bad request with error message"() {
        when:
            def resultActions = makeRequest("POL", "BRA")
        then:
            resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"message\":\"There's no land crossing\"}"))
    }

    def "When requested route between countries does not exist, it should return bad request with error message"() {
        when:
            def resultActions = makeRequest("CHN", "JPN")
        then:
            resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"message\":\"There's no land crossing\"}"))
    }

    private ResultActions makeRequest(final String origin, final String destination) {
        return mockMvc.perform(get('/routing/{origin}/{destination}', origin, destination))
    }
}
