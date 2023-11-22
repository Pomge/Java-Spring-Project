package ru.lanit.test.services;

import java.util.HashMap;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ru.lanit.test.dto.CarDTO;
import ru.lanit.test.models.CarModel;
import ru.lanit.test.models.PersonModel;
import ru.lanit.test.repositories.CarRepository;
import ru.lanit.test.util.CarModelValidator;
import ru.lanit.test.util.NotCreatedException;

@Service
@Validated
@Transactional(readOnly = true)
public class CarService {
	private final ModelMapper modelMapper;
	private final CarRepository carRepository;
	private final CarModelValidator carModelValidator;

	@Autowired
	public CarService(ModelMapper modelMapper, CarRepository carRepository, CarModelValidator carModelValidator) {
		this.modelMapper = modelMapper;
		this.carRepository = carRepository;
		this.carModelValidator = carModelValidator;
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

		MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), PersonModel.class.getName());

		long id = carModel.getId();
		if (carRepository.existsById(id)) {
			errors.rejectValue("id", "", "This \'id\' is already exist");
		}
		carModelValidator.validate(carModel, errors);

		if (errors.hasErrors()) {
			throw new NotCreatedException("Backend Validation Errors", errors);
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
