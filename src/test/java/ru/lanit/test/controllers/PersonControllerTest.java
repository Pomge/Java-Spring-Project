package ru.lanit.test.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.lanit.test.advice.MyExceptionHandler;
import ru.lanit.test.dto.PersonDTO;
import ru.lanit.test.models.PersonModel;
import ru.lanit.test.services.PersonService;
import ru.lanit.test.util.NotCreatedException;

@AutoConfigureMockMvc
@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {
	@MockBean
	private PersonService personService;

	private MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final ModelMapper modelMapper;

	@Autowired
	public PersonControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, ModelMapper modelMapper) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.modelMapper = modelMapper;
	}

	@BeforeEach
	public void beforeTests() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(personService))
				.setControllerAdvice(new MyExceptionHandler()).build();
	}

	@Test
	void addPersonTest404BadRequest() throws Exception {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setId(null);
		personDTO.setName(null);
		personDTO.setBirthdate(null);

		when(personService.save(any(PersonDTO.class))).thenThrow(new NotCreatedException("", "", ""));

		String body = objectMapper.writeValueAsString(personDTO);

		this.mockMvc.perform(
				post("/person").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(body))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void addPersonTest200Ok() throws Exception {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setId((long) 1);
		personDTO.setName("TestName");
		personDTO.setBirthdate(LocalDate.parse("22.11.2000", DateTimeFormatter.ofPattern("dd.MM.yyyy")));

		when(personService.save(any(PersonDTO.class))).thenReturn(convertToPersonModel(personDTO));

		String body = objectMapper.writeValueAsString(personDTO);

		this.mockMvc.perform(
				post("/person").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(body))
				.andDo(print()).andExpect(status().isOk());
	}

	private PersonModel convertToPersonModel(PersonDTO personDTO) {
		return modelMapper.map(personDTO, PersonModel.class);
	}
}
