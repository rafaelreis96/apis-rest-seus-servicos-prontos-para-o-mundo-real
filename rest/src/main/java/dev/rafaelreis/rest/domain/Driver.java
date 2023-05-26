package dev.rafaelreis.rest.domain;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Schema(description = "Representa um motorista dentro da plataforma")
public class Driver {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=5, max=255)
	@Schema(description="Nome do motorista")
	private String name;
	
	@Schema(description="Data de nascimento do motorista")
	private Date birthDate;
}
