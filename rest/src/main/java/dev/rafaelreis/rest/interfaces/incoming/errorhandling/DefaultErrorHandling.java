package dev.rafaelreis.rest.interfaces.incoming.errorhandling;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestControllerAdvice
public class DefaultErrorHandling {
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ApiResponses(
		@ApiResponse(responseCode="400", content = @Content(
			mediaType = "application/json",
			schema = @Schema(implementation = ErrorResponse.class)
		))
	)
	public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		
		List<ErrorData> messages = ex
				.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(this::getMessage)
				.collect(Collectors.toList());
		
		return new ErrorResponse(messages);
	}
	
	private ErrorData getMessage(FieldError fiedError) {
		return new ErrorData(messageSource.getMessage(fiedError, LocaleContextHolder.getLocale()));
	}
	
}
