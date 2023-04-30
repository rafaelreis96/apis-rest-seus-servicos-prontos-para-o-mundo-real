package dev.rafaelreis.rest.interfaces.input;

import lombok.Data;

@Data
public class TravelRequestInput {

	private Long passengerId;
	private String origin;
	private String destination;
}
