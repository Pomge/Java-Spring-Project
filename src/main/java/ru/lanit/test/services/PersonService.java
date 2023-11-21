package ru.lanit.test.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ru.lanit.test.dto.CarDTO;
import ru.lanit.test.dto.PersonDTO;
import ru.lanit.test.dto.PersonWithCarsDTO;
import ru.lanit.test.models.CarModel;
import ru.lanit.test.models.PersonModel;
import ru.lanit.test.repositories.PersonRepository;
import ru.lanit.test.util.NotCreatedException;

@Service
@Validated
@Transactional(readOnly = true)
public class PersonService {

	private final ModelMapper modelMapper;
	private final PersonRepository personRepository;

	@Autowired
	public PersonService(ModelMapper modelMapper, PersonRepository personRepository) {
		this.modelMapper = modelMapper;
		this.personRepository = personRepository;
	}

	public PersonModel getPersonModelById(long id) {
		Optional<PersonModel> optional = personRepository.findById(id);

		if (optional.isPresent()) {
			PersonModel personModel = optional.get();
			return personModel;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "\'personid=" + id + "' not found");
		}
	}

	public PersonDTO getPersonDTOById(long id) {
		PersonModel personModel = getPersonModelById(id);
		PersonDTO personDTO = convertToPersonDTO(personModel);
		return personDTO;
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
	public void save(@Valid PersonDTO personDTO) {
		PersonModel personModel = convertToPersonModel(personDTO);

		long id = personModel.getId();

		NotCreatedException notCreatedException = new NotCreatedException("Backend Validation Errors");
		Optional<PersonModel> optional = personRepository.findById(id);
		if (optional.isPresent()) {
			PersonModel personModelFromDataBase = optional.get();
			long dataBase_id = personModelFromDataBase.getId();

			if (id == dataBase_id) {
				notCreatedException.addException("id", "This \'id\' is already exist");
			}
		}

		LocalDate birthdateDate = personModel.getBirthdate();
		LocalDate today = LocalDate.now();

		if (birthdateDate.isAfter(today)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			String formattedString = today.format(formatter);
			notCreatedException.addException("birthdate", "Must be before current date " + formattedString);
		}

		if (!notCreatedException.getExceptions().isEmpty()) {
			throw notCreatedException;
		}

		personRepository.save(personModel);
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
