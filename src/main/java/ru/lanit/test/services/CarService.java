package ru.lanit.test.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ru.lanit.test.dto.CarDTO;
import ru.lanit.test.models.CarModel;
import ru.lanit.test.models.PersonModel;
import ru.lanit.test.repositories.CarRepository;
import ru.lanit.test.repositories.PersonRepository;
import ru.lanit.test.util.NotCreatedException;

@Service
@Validated
@Transactional(readOnly = true)
public class CarService {
	private final ModelMapper modelMapper;
	private final PersonRepository personRepository;
	private final CarRepository carRepository;

	@Autowired
	public CarService(ModelMapper modelMapper, PersonRepository personRepository, CarRepository carRepository) {
		this.modelMapper = modelMapper;
		this.personRepository = personRepository;
		this.carRepository = carRepository;
	}

	public CarModel getCarModelById(long id) {
		Optional<CarModel> optional = carRepository.findById(id);

		if (optional.isPresent()) {
			CarModel carModel = optional.get();
			return carModel;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "\'carid=" + id + "' not found");
		}
	}

	@Transactional
	public void save(@Valid CarDTO carDTO) {
		CarModel carModel = convertToCarModel(carDTO);
		long carId = carModel.getId();

		NotCreatedException notCreatedException = new NotCreatedException("Backend Validation Errors");
		Optional<CarModel> optionalCar = carRepository.findById(carId);
		if (optionalCar.isPresent()) {
			CarModel carModelFromDataBase = optionalCar.get();
			long dataBase_id = carModelFromDataBase.getId();

			if (carId == dataBase_id) {
				notCreatedException.addException("id", "This \'id\' is already exist");
			}
		}

		long ownerId = carModel.getOwner().getId();
		Optional<PersonModel> optionalPerson = personRepository.findById(ownerId);
		if (!optionalPerson.isPresent()) {
			notCreatedException.addException("ownerId", "This \'ownerId\' does not exist");
		} else {
			PersonModel owner = optionalPerson.get();
			LocalDate birthdate = owner.getBirthdate();
			LocalDate today = LocalDate.now();

			long yearsDifference = Period.between(birthdate, today).getYears();
			if (yearsDifference < 18) {
				today.minusYears(18);
				notCreatedException.addException("ownerId", "Owner's birthdate should be before " + today);
			}
		}

		if (!notCreatedException.getExceptions().isEmpty()) {
			throw notCreatedException;
		}

		carRepository.save(carModel);
	}

	@Transactional
	public void truncateTable() {
		carRepository.deleteAll();
	}

	private CarModel convertToCarModel(CarDTO carDTO) {
		return modelMapper.map(carDTO, CarModel.class);
	}
}
