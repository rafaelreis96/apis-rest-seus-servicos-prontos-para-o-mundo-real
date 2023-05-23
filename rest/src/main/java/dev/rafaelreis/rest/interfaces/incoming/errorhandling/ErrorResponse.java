package dev.rafaelreis.rest.interfaces.incoming.errorhandling;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {

	private List<ErrorData> errors;
	
}
