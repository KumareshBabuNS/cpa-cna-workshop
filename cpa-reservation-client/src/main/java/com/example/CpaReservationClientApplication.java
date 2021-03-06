package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class CpaReservationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CpaReservationClientApplication.class, args);
	}
}

@RequestMapping("/reservations")
@RestController
class ReservationController {
	
	
	@RequestMapping("/names")
	public Collection<String> getAllReservationName(){
		
		RestTemplate rt = new RestTemplate();
		
		rt.setMessageConverters(getHttpMessageConverters());
		
		ResponseEntity<Resources<Reservation>> response = 
				rt.exchange("http://cpa-reservation-service.cfapps.io/reservations", 
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<Resources<Reservation>>(){}
				);
		
		
		
		List<String> list = new ArrayList<String>(response.getBody()
				.getContent()
				.stream()
				.map(Reservation::getName)
				.collect(Collectors.toList()));
		
		
		list.add("DUMMY V2!!!!");
		return list;
		
	}
	
	private List<HttpMessageConverter<?>> getHttpMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jackson2HalModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setObjectMapper(mapper);
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converters.add(converter);

        return converters;
}
	
}

class Reservation{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

