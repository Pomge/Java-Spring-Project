package ru.lanit.test.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ru.lanit.test.dto.StatisticsDTO;
import ru.lanit.test.services.StatisticsService;

@AutoConfigureMockMvc
@WebMvcTest(controllers = StatisticsController.class)
class StatisticsControllerTest {
	@MockBean
	private StatisticsService statisticsService;

	private final MockMvc mockMvc;

	@Autowired
	public StatisticsControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Test
	void getStatisticsDTO_200Ok() throws Exception {
		long personcount = 0;
		long carcount = 0;
		long uniquevendorcount = 0;

		StatisticsDTO statisticsDTO = new StatisticsDTO();
		statisticsDTO.setPersoncount(personcount);
		statisticsDTO.setCarcount(carcount);
		statisticsDTO.setUniquevendorcount(uniquevendorcount);

		when(statisticsService.getStatisticsDTO()).thenReturn(statisticsDTO);

		this.mockMvc.perform(get("/statistics")).andExpect(status().isOk());
	}
}
