package dev.rafaelreis.rest.interfaces.incoming;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.rafaelreis.rest.domain.Driver;
import dev.rafaelreis.rest.domain.DriverRepository;
import dev.rafaelreis.rest.interfaces.incoming.output.Drivers;

@Service
@RestController
@RequestMapping(path="/drivers", produces = MediaType.APPLICATION_JSON_VALUE)
public class DriverAPIImpl implements DriverAPI {
	
	private static final int PAGE_SIZE = 10;

	@Autowired
	private DriverRepository driverRepository;

	@GetMapping
	public Drivers listDrivers(@RequestParam(name="page", defaultValue="0") int page) {
		Page<Driver> driverPage = driverRepository.findAll(PageRequest.of(page, PAGE_SIZE));
		CollectionModel<Driver> collectionModel = CollectionModel.of(driverPage.getContent());
		
		List<EntityModel<Driver>> driverList = new ArrayList<>();
		for(Driver driver : driverPage.getContent()) {
			driverList.add(EntityModel.of(driver));
		}
		
		Link lastPageLink = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(this.getClass()).listDrivers(driverPage.getTotalPages() - 1))
				.withRel("lastPage");
		
		return new Drivers(driverList, lastPageLink);
	}

	@RequestMapping("/{id}")
	public Driver findDriver(@PathVariable("id") Long id) {
		return driverRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public Driver createDriver(@RequestBody Driver driver) {
		return driverRepository.save(driver);
	}

	@PutMapping
	public Driver fullUpdateDriver(@PathVariable("id") Long id, @RequestBody Driver driver) {
		Driver foundDriver = findDriver(id);
		foundDriver.setBirthDate(driver.getBirthDate());
		foundDriver.setName(driver.getName());
		return driverRepository.save(foundDriver);
	}

	@PatchMapping("/{id}")
	public Driver incrementalUpdateDriver(@PathVariable("id") Long id, @RequestBody Driver driver) {
		Driver foundDriver = findDriver(id);
		foundDriver.setBirthDate(Optional.ofNullable(driver.getBirthDate()).orElse(foundDriver.getBirthDate()));
		foundDriver.setName(Optional.ofNullable(driver.getName()).orElse(foundDriver.getName()));
		return driverRepository.save(foundDriver);
	}

	@DeleteMapping("/{id}")
	public void deleteDriver(@PathVariable("id") Long id) {
		driverRepository.delete(findDriver(id));
	}

}
