package ru.lanit.test.controllers;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ru.lanit.test.services.CarService;
import ru.lanit.test.services.PersonService;

@AutoConfigureMockMvc
@WebMvcTest(controllers = UtilityController.class)
class UtilityControllerTest {
	@MockBean
	private PersonService personService;
	@MockBean
	private CarService carService;

	private MockMvc mockMvc;

	@Autowired
	public UtilityControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Test
	void trunceAllTables_200Ok() throws Exception {
		doNothing().when(personService).deleteAll();

		this.mockMvc.perform(get("/clear")).andExpect(status().isOk());
	}
}
