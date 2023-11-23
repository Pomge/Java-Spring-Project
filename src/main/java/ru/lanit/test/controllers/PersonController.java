package ru.lanit.test.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.lanit.test.dto.PersonDTO;
import ru.lanit.test.dto.PersonWithCarsDTO;
import ru.lanit.test.services.PersonService;

@RestController
@RequiredArgsConstructor
public class PersonController {

	private final PersonService personService;

	@PostMapping("/person")
	public ResponseEntity<Object> addPersonDTO(@RequestBody PersonDTO personDTO) {
		personService.savePersonDTO(personDTO);
		return ResponseEntity.ok(null);
	}

	@GetMapping("/personwithcars")
	public PersonWithCarsDTO getPersonWithCarsDTOById(@RequestParam(name = "personid") Long personid) {
		PersonWithCarsDTO personWithCarsDTO = personService.getPersonWithCarsDTOById(personid);
		return personWithCarsDTO;
	}
}
