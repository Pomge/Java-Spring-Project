package ru.lanit.test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.lanit.test.dto.StatisticsDTO;
import ru.lanit.test.services.StatisticsService;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

	private final StatisticsService statisticsService;

	@GetMapping("/statistics")
	public StatisticsDTO getStatistics() {
		return statisticsService.getStatisticsDTO();
	}
}
