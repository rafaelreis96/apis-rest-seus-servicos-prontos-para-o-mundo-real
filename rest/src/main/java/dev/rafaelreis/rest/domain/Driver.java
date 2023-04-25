package dev.rafaelreis.rest.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Driver {
	@Id
	private Long id;
	private String name;
	private Date birthDate;
}
