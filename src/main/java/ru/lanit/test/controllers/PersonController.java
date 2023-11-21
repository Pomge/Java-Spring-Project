package ru.lanit.test.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ru.lanit.test.dto.CarDTO;
import ru.lanit.test.dto.PersonDTO;
import ru.lanit.test.dto.PersonWithCarsDTO;
import ru.lanit.test.models.CarModel;
import ru.lanit.test.models.PersonModel;
import ru.lanit.test.services.PersonService;
import ru.lanit.test.util.NotCreatedException;
import ru.lanit.test.util.PersonValidator;

@RestController
public class PersonController {

	private final ModelMapper modelMapper;
	private final PersonService personService;
	private final PersonValidator personValidator;

	@Autowired
	public PersonController(ModelMapper modelMapper, PersonService personService, PersonValidator personValidator) {
		this.modelMapper = modelMapper;
		this.personService = personService;
		this.personValidator = personValidator;
	}

	@PostMapping("/person")
	public ResponseEntity<HttpStatus> addPerson(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new NotCreatedException("Frontend validation errors", bindingResult.getFieldErrors());
		} else {
			PersonModel personModel = convertToPersonModel(personDTO);
			personValidator.validate(personModel, bindingResult);

			if (bindingResult.hasErrors()) {
				throw new NotCreatedException("Backend validation errors", bindingResult.getFieldErrors());
			} else {
				personService.save(personModel);
				return ResponseEntity.ok(HttpStatus.OK);
			}
		}
	}

	@GetMapping("/personwithcars")
	public PersonWithCarsDTO getPerson(@RequestParam(name = "personid") Long personid) {
		Optional<PersonModel> optional = personService.findById(personid);

		if (optional.isPresent()) {
			PersonModel personModel = optional.get();
			PersonDTO personDTO = convertToPersonDTO(personModel);

			List<CarModel> carModelList = personModel.getCars();
			List<CarDTO> cars = carModelList.stream().map(carModel -> convertToCarDTO(carModel))
					.collect(Collectors.toList());

			PersonWithCarsDTO personWithCarsDTO = new PersonWithCarsDTO(personDTO, cars);
			System.out.println("AAA");

			return personWithCarsDTO;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "\'personid=" + personid + "' not found");
		}
	}

	private PersonModel convertToPersonModel(PersonDTO personDTO) {
		return modelMapper.map(personDTO, PersonModel.class);
	}

	private PersonDTO convertToPersonDTO(PersonModel personModel) {
		return modelMapper.map(personModel, PersonDTO.class);
	}

	private CarDTO convertToCarDTO(CarModel carModel) {
		return modelMapper.map(carModel, CarDTO.class);
	}
}
