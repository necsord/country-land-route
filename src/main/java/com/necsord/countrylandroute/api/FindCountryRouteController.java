package com.necsord.countrylandroute.api;

import com.necsord.countrylandroute.domain.FindCountryRouteUC;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
class FindCountryRouteController {
    FindCountryRouteUC uc;

    @GetMapping(value = "/routing/{origin}/{destination}")
    @ResponseBody
    public FindCountryRouteResponse find(@PathVariable String origin, @PathVariable String destination) {
        return new FindCountryRouteResponse(uc.find(origin, destination));
    }
}
