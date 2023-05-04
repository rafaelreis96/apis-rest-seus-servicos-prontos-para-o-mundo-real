package dev.rafaelreis.rest.interfaces.incoming.mapping;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import dev.rafaelreis.rest.domain.Passenger;
import dev.rafaelreis.rest.domain.PassengerRepository;
import dev.rafaelreis.rest.domain.TravelRequest;
import dev.rafaelreis.rest.interfaces.incoming.PassengerAPI;
import dev.rafaelreis.rest.interfaces.incoming.input.TravelRequestInput;
import dev.rafaelreis.rest.interfaces.incoming.output.TravelRequestOutput;

@Component
public class TravelRequestMapper {

	@Autowired
	private PassengerRepository passengerRepository;
	
	public TravelRequest map(TravelRequestInput travelRequestInput) {
		Passenger passenger = passengerRepository
				.findById(travelRequestInput.getPassengerId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		TravelRequest travelRequest = new TravelRequest();
		travelRequest.setOrigin(travelRequestInput.getOrigin());
		travelRequest.setDestination(travelRequestInput.getDestination());
		travelRequest.setPassenger(passenger);
		
		return travelRequest;
	}
	
	public TravelRequestOutput map(TravelRequest travelRequest) {
		TravelRequestOutput travelRequestOutput = new TravelRequestOutput();
		travelRequestOutput.setCreationDate(new Date());
		travelRequestOutput.setDestination(travelRequest.getDestination());
		travelRequestOutput.setId(travelRequest.getId());
		travelRequestOutput.setOrigin(travelRequest.getOrigin());
		travelRequestOutput.setStatus(travelRequest.getStatus());
		
		return travelRequestOutput;
	}
	
	public EntityModel<TravelRequestOutput> buildOutputModel(TravelRequest travelRequest, 
			TravelRequestOutput travelRequestOutput) {
		
		EntityModel<TravelRequestOutput> model = EntityModel.of(travelRequestOutput);
		
		Link passengerLink = WebMvcLinkBuilder
				.linkTo(PassengerAPI.class)
				.slash(travelRequest.getPassenger().getId())
				.withRel("passenger")
				.withTitle(travelRequest.getPassenger().getName());
		
		model.add(passengerLink);
		
		return model;
	}
	
	public List<EntityModel<TravelRequestOutput>> buildOutputModel(List<TravelRequest> requests) {
		return requests
				.stream()
				.map(tr -> buildOutputModel(tr, map(tr)))
				.collect(Collectors.toList());
	}
	
}
