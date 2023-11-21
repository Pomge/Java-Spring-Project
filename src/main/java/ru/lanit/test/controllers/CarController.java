package ru.lanit.test.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.lanit.test.dto.CarDTO;
import ru.lanit.test.models.CarModel;
import ru.lanit.test.services.CarService;
import ru.lanit.test.util.CarValidator;
import ru.lanit.test.util.NotCreatedException;

@RestController
public class CarController {

	private final ModelMapper modelMapper;
	private final CarService carService;
	private final CarValidator carValidator;

	@Autowired
	public CarController(ModelMapper modelMapper, CarService carService, CarValidator carValidator) {
		super();
		this.modelMapper = modelMapper;
		this.carService = carService;
		this.carValidator = carValidator;
	}

	@PostMapping("/car")
	public ResponseEntity<HttpStatus> addCar(@RequestBody @Valid CarDTO carDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new NotCreatedException("Frontend validation errors", bindingResult.getFieldErrors());
		} else {
			CarModel carModel = convertToCarModel(carDTO);
			carValidator.validate(carModel, bindingResult);
			if (bindingResult.hasErrors()) {
				throw new NotCreatedException("Backend validation errors", bindingResult.getFieldErrors());
			} else {
				carService.save(carModel);
				return ResponseEntity.ok(HttpStatus.OK);
			}
		}
	}

	private CarModel convertToCarModel(CarDTO carDTO) {
		return modelMapper.map(carDTO, CarModel.class);
	}
}
