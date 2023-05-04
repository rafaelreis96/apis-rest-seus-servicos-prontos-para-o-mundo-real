package dev.rafaelreis.rest.interfaces.incoming.output;

import java.util.Date;

import dev.rafaelreis.rest.domain.TravelRequestStatus;
import lombok.Data;

@Data
public class TravelRequestOutput {

	private Long id;
	private String origin;
	private String destination;
	private TravelRequestStatus status;
	private Date creationDate;
	
}
