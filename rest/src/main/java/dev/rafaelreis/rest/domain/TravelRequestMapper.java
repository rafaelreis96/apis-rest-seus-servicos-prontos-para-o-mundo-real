package dev.rafaelreis.rest.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import dev.rafaelreis.rest.interfaces.PassengerAPI;
import dev.rafaelreis.rest.interfaces.input.TravelRequestInput;

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
	
	
}
