package ru.lanit.test.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.lanit.test.services.CarService;
import ru.lanit.test.services.PersonService;

@RestController
public class UtilityController {

	private final PersonService personService;
	private final CarService carService;

	public UtilityController(PersonService personService, CarService carService) {
		this.personService = personService;
		this.carService = carService;
	}

	@GetMapping("/clear")
	public ResponseEntity<HttpStatus> clear() {
		carService.truncateTable();
		personService.truncateTable();
		return ResponseEntity.ok(HttpStatus.OK);
	}
}