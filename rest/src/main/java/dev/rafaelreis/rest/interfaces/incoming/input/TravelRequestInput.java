package dev.rafaelreis.rest.interfaces.incoming.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TravelRequestInput {
	@NotNull
	private Long passengerId;
	
	@NotEmpty
	private String origin;
	
	@NotEmpty
	private String destination;
}
