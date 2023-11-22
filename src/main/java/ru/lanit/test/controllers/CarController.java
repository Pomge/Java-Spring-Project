package ru.lanit.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.lanit.test.dto.CarDTO;
import ru.lanit.test.services.CarService;

@RestController
public class CarController {

	private final CarService carService;

	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	@PostMapping("/car")
	public ResponseEntity<Object> addCar(@RequestBody CarDTO carDTO) {
		carService.save(carDTO);
		return ResponseEntity.ok(null);
	}
}
