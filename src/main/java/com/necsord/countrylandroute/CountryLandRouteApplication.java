package com.necsord.countrylandroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

@SpringBootApplication
@ComponentScan(basePackages = "com.necsord.countrylandroute", includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Configuration.class, Controller.class, ControllerAdvice.class}), useDefaultFilters = false)
public class CountryLandRouteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryLandRouteApplication.class, args);
	}

}
