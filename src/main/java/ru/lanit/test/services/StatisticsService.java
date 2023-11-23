package ru.lanit.test.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.lanit.test.dto.StatisticsDTO;
import ru.lanit.test.repositories.CarRepository;
import ru.lanit.test.repositories.PersonRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {
	private final PersonRepository personRepository;
	private final CarRepository carRepository;

	public StatisticsDTO getStatisticsDTO() {
		long personcount = personRepository.count();
		long carcount = carRepository.count();
		long uniquevendorcount = carRepository.countDistinctVendors();

		StatisticsDTO statisticsDTO = new StatisticsDTO(personcount, carcount, uniquevendorcount);
		return statisticsDTO;
	}
}
