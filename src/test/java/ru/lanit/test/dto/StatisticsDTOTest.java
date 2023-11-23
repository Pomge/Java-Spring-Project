package ru.lanit.test.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatisticsDTOTest {
	private StatisticsDTO statisticsDTO;

	@BeforeEach
	public void beforeEach() {
		statisticsDTO = new StatisticsDTO();
	}

	@AfterEach
	public void afterEach() {
		statisticsDTO = null;
	}

	@Test
	public void testGetterSetterAnnotation() {
		long personcount = 0;
		long carcount = 0;
		long uniquevendorcount = 0;

		statisticsDTO.setPersoncount(personcount);
		statisticsDTO.setCarcount(carcount);
		statisticsDTO.setUniquevendorcount(uniquevendorcount);

		assertEquals(personcount, statisticsDTO.getPersoncount());
		assertEquals(carcount, statisticsDTO.getCarcount());
		assertEquals(uniquevendorcount, statisticsDTO.getUniquevendorcount());
	}

	@Test
	public void testDataAnnotationForNoArgsConstructor() {
		statisticsDTO = new StatisticsDTO();
		assertNotNull(statisticsDTO);
	}

	@Test
	public void testDataAnnotationForAllArgsConstructor() {
		long personcount = 0;
		long carcount = 0;
		long uniquevendorcount = 0;

		statisticsDTO = new StatisticsDTO(personcount, carcount, uniquevendorcount);
		assertNotNull(statisticsDTO);
	}
}
