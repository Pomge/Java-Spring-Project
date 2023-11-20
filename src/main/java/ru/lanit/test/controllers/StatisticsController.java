package ru.lanit.test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.lanit.test.dto.StatisticsDTO;
import ru.lanit.test.services.CarService;
import ru.lanit.test.services.PersonService;

@RestController
public class StatisticsController {

	private final PersonService personService;
	private final CarService carService;

	public StatisticsController(PersonService personService, CarService carService) {
		this.personService = personService;
		this.carService = carService;
	}

	@GetMapping("/statistics")
	public StatisticsDTO getStatistics() {
		long personcount = personService.getPersonCount();
		long carcount = carService.getCarCount();
		long uniquevendorcount = carService.getDistinctVendorsCount();

		StatisticsDTO statisticsDTO = new StatisticsDTO(personcount, carcount, uniquevendorcount);
		return statisticsDTO;
	}
}
