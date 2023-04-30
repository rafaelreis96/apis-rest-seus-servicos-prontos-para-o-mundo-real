package dev.rafaelreis.rest.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.rafaelreis.rest.domain.TravelRequest;
import dev.rafaelreis.rest.domain.TravelRequestMapper;
import dev.rafaelreis.rest.domain.TravelRequestOutput;
import dev.rafaelreis.rest.domain.TravelService;
import dev.rafaelreis.rest.interfaces.input.TravelRequestInput;


@Service
@RestController
@RequestMapping(path = "/travelRequest", produces = MediaType.APPLICATION_JSON_VALUE)
public class TravelRequestAPI {
	
	@Autowired
	private  TravelService travelService;
	
	@Autowired
	private TravelRequestMapper mapper;
	
	@PostMapping
	public EntityModel<TravelRequestOutput> makeTravelRequet(@RequestBody TravelRequestInput travelRequestInput) {
		TravelRequest request =  travelService.saveTravelRequest(mapper.map(travelRequestInput));
		TravelRequestOutput output = mapper.map(request);
		return mapper.buildOutputModel(request, output);
	}

}
