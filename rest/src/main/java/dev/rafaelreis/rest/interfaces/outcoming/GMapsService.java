package dev.rafaelreis.rest.interfaces.outcoming;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

@Service
public class GMapsService {

	@Value("${app.car.domain.googlemaps.apikey}")
	private String appKey;
	
	@Value("${interfaces.outcoming.gmaps.host:https://maps.googleapis.com}")
	private String gMapsHost;

	private final static String GMAPS_TAMPLATE = "https://maps.googleapis.com/maps/api/directions/json?"
			+ "origin={origin}&destination={destination}&key={key}";

	public Integer getDistanceBetweenAndresses(String addressOne, String addressTwo) {
		RestTemplate template = new RestTemplate();
		String jsonResult = template.getForObject(GMAPS_TAMPLATE, 
			String.class, addressOne, addressTwo, appKey);

		JSONArray rawResults = JsonPath.parse(jsonResult).read("$..legs[*].duration.value");

		List<Integer> results = rawResults.stream()
			.map( it -> ((Integer) it))
			.collect(Collectors.toList());

		return results.stream()
			.min(Integer::compareTo)
			.orElse(Integer.MAX_VALUE);
	}

}
