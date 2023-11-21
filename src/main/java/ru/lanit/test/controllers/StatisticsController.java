package ru.lanit.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.lanit.test.dto.StatisticsDTO;
import ru.lanit.test.services.StatisticsService;

@RestController
public class StatisticsController {

	private final StatisticsService statisticsService;

	@Autowired
	public StatisticsController(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	@GetMapping("/statistics")
	public StatisticsDTO getStatistics() {
		return statisticsService.getStatisticsDTO();
	}
}
