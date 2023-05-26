package dev.rafaelreis.rest.interfaces.incoming;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.rafaelreis.rest.domain.Driver;
import dev.rafaelreis.rest.interfaces.incoming.errorhandling.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Driver API", description = "Manipula dados de motoristas.")
public interface DriverAPI {

	@Operation(description = "Lista todos os motoristas disponíveis")
	public List<Driver> listDrivers();
	
	@RequestMapping("/drivers/{id}")
	@Operation(description="Localiza um motorista especifico", responses = {
			@ApiResponse(responseCode="200", description="Caso o motorista tenha sido encontrado na base"),
			@ApiResponse(
					responseCode="404", 
					description="Caso o motorista não tenha sido encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public Driver findDriver(
			@Parameter(description = "ID do motorista a ser localizado")
			@PathVariable("id") Long id);
	
	public Driver createDriver(@Parameter(description = "Dados do motorista a ser criado") @RequestBody Driver driver);
	
	public Driver fullUpdateDriver(@PathVariable("id") Long id, @RequestBody Driver driver);
	
	public Driver incrementalUpdateDriver(@PathVariable("id") Long id, @RequestBody Driver driver);
	
	public void deleteDriver(@PathVariable("id") Long id);
	
}
