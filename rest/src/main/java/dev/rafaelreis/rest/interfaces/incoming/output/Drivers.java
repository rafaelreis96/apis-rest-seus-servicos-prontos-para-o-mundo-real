package dev.rafaelreis.rest.interfaces.incoming.output;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import dev.rafaelreis.rest.domain.Driver;
import lombok.Getter;

@Getter
public class Drivers {

	private List<EntityModel<Driver>> drivers;

	private Link[] links;

	public Drivers(List<EntityModel<Driver>> drivers, Link... links) {
		super();
		this.drivers = drivers;
		this.links = links;
	}

}
