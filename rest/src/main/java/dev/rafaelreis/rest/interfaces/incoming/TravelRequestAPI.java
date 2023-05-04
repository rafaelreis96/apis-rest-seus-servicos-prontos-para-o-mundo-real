package dev.rafaelreis.rest.interfaces.incoming;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.rafaelreis.rest.domain.TravelRequest;
import dev.rafaelreis.rest.domain.TravelService;
import dev.rafaelreis.rest.interfaces.incoming.input.TravelRequestInput;
import dev.rafaelreis.rest.interfaces.incoming.mapping.TravelRequestMapper;
import dev.rafaelreis.rest.interfaces.incoming.output.TravelRequestOutput;

@Service
@RestController
@RequestMapping(path = "/travelRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class TravelRequestAPI {

	@Autowired
	private TravelService travelService;

	@Autowired
	private TravelRequestMapper mapper;

	@PostMapping
	public EntityModel<TravelRequestOutput> makeTravelRequet(@RequestBody TravelRequestInput travelRequestInput) {
		TravelRequest request = travelService.saveTravelRequest(mapper.map(travelRequestInput));
		TravelRequestOutput output = mapper.map(request);
		return mapper.buildOutputModel(request, output);
	}
	
	@GetMapping("/nearby")
	public List<EntityModel<TravelRequestOutput>> listNeabyRequests(@RequestParam String currentAddress) {
		List<TravelRequest> requests = travelService.listNearbyTravelRequests(currentAddress);
		return mapper.buildOutputModel(requests);
	}

}
