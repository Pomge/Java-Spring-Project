package ru.lanit.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.lanit.test.dto.StatisticsDTO;
import ru.lanit.test.repositories.CarRepository;
import ru.lanit.test.repositories.PersonRepository;

@Service
@Transactional(readOnly = true)
public class StatisticsService {
	private final PersonRepository personRepository;
	private final CarRepository carRepository;

	@Autowired
	public StatisticsService(PersonRepository personRepository, CarRepository carRepository) {
		this.personRepository = personRepository;
		this.carRepository = carRepository;
	}

	public StatisticsDTO getStatisticsDTO() {
		long personcount = personRepository.count();
		long carcount = carRepository.count();
		long uniquevendorcount = carRepository.countDistinctVendors();

		StatisticsDTO statisticsDTO = new StatisticsDTO(personcount, carcount, uniquevendorcount);
		return statisticsDTO;
	}
}
