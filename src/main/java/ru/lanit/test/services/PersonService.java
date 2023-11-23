package ru.lanit.test.services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.lanit.test.dto.CarDTO;
import ru.lanit.test.dto.PersonDTO;
import ru.lanit.test.dto.PersonWithCarsDTO;
import ru.lanit.test.models.CarModel;
import ru.lanit.test.models.PersonModel;
import ru.lanit.test.repositories.PersonRepository;
import ru.lanit.test.util.NotCreatedException;
import ru.lanit.test.util.PersonModelValidator;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {

	private final ModelMapper modelMapper;
	private final PersonRepository personRepository;
	private final PersonModelValidator personModelValidator;

	public PersonModel getPersonModelById(long id) {
		Optional<PersonModel> optional = personRepository.findById(id);

		if (optional.isPresent()) {
			PersonModel personModel = optional.get();
			return personModel;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "\'personId=" + id + "' not found");
		}
	}

	public PersonWithCarsDTO getPersonWithCarsDTO(long id) {
		PersonModel personModel = getPersonModelById(id);
		PersonDTO personDTO = convertToPersonDTO(personModel);

		List<CarModel> carModels = personModel.getCars();
		List<CarDTO> carDTOs = carModels.stream().map(car -> convertToCarDTO(car)).collect(Collectors.toList());

		PersonWithCarsDTO personWithCarsDTO = new PersonWithCarsDTO(personDTO, carDTOs);
		return personWithCarsDTO;
	}

	@Transactional
	public PersonModel save(@Valid PersonDTO personDTO) {
		PersonModel personModel = convertToPersonModel(personDTO);

		MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), PersonModel.class.getName());

		long id = personModel.getId();
		if (personRepository.existsById(id)) {
			errors.rejectValue("id", "", "This \'id\' is already exist");
		}
		personModelValidator.validate(personModel, errors);

		if (errors.hasErrors()) {
			throw new NotCreatedException("Backend Validation Errors", errors);
		}

		personRepository.save(personModel);
		return personModel;
	}

	@Transactional
	public void truncateTable() {
		personRepository.deleteAll();
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
