package ru.lanit.test.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.lanit.test.services.CarService;
import ru.lanit.test.services.PersonService;

@RestController
@RequiredArgsConstructor
public class UtilityController {

	private final PersonService personService;
	private final CarService carService;

	@GetMapping("/clear")
	public ResponseEntity<Object> trunceAllTables() {
		carService.deleteAll();
		personService.deleteAll();
		return ResponseEntity.ok(null);
	}
}
