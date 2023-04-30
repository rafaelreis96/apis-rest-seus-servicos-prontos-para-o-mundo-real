package dev.rafaelreis.rest.domain;

import java.util.Date;

import lombok.Data;

@Data
public class TravelRequestOutput {

	private Long id;
	private String origin;
	private String destination;
	private TravelRequestStatus status;
	private Date creationDate;
	
}
