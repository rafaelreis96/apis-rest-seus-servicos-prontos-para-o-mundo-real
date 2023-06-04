package dev.rafaelreis.rest.interfaces.incoming;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.rafaelreis.rest.domain.Driver;
import dev.rafaelreis.rest.interfaces.incoming.errorhandling.ErrorResponse;
import dev.rafaelreis.rest.interfaces.incoming.output.Drivers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Driver API", description = "Manipula dados de motoristas.")
public interface DriverAPI {

	@Operation(description = "Lista todos os motoristas disponíveis")
	public Drivers listDrivers(@Parameter(description = "Número da página") int page);
	
	@RequestMapping("/drivers/{id}")
	@Operation(description="Localiza um motorista especifico", responses = {
			@ApiResponse(responseCode="200", description="Caso o motorista tenha sido encontrado na base"),
			@ApiResponse(
					responseCode="404", 
					description="Caso o motorista não tenha sido encontrado",
					content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public Driver findDriver(
			@Parameter(description = "ID do motorista a ser localizado") Long id);
	
	public Driver createDriver(@Parameter(description = "Dados do motorista a ser criado") Driver driver);
	
	public Driver fullUpdateDriver(Long id, @RequestBody Driver driver);
	
	public Driver incrementalUpdateDriver(Long id, @RequestBody Driver driver);
	
	public void deleteDriver(Long id);
	
}
