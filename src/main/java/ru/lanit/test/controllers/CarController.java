package ru.lanit.test.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.lanit.test.dto.CarDTO;
import ru.lanit.test.services.CarService;

@RestController
@RequiredArgsConstructor
public class CarController {

	private final CarService carService;

	@PostMapping("/car")
	public ResponseEntity<Object> addCarDTO(@RequestBody CarDTO carDTO) {
		carService.saveCarDTO(carDTO);
		return ResponseEntity.ok(null);
	}
}
