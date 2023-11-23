package ru.lanit.test.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.lanit.test.advice.MyExceptionHandler;
import ru.lanit.test.dto.CarDTO;
import ru.lanit.test.dto.PersonDTO;
import ru.lanit.test.dto.PersonWithCarsDTO;
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
	public void beforeEach() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(personService))
				.setControllerAdvice(new MyExceptionHandler()).build();
	}

	@Test
	void addPersonDTO_200Ok() throws Exception {
		long id = 0;
		String name = "Test";
		LocalDate birthdate = LocalDate.now();

		PersonDTO personDTO = new PersonDTO();
		personDTO.setId(id);
		personDTO.setName(name);
		personDTO.setBirthdate(birthdate);

		when(personService.savePersonDTO(any(PersonDTO.class))).thenReturn(convertToPersonModel(personDTO));

		String body = objectMapper.writeValueAsString(personDTO);

		this.mockMvc.perform(
				post("/person").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(body))
				.andExpect(status().isOk());
	}

	@Test
	void addPersonDTO_400BadRequest() throws Exception {
		Long id = null;
		String name = null;
		LocalDate birthdate = null;

		PersonDTO personDTO = new PersonDTO();
		personDTO.setId(id);
		personDTO.setName(name);
		personDTO.setBirthdate(birthdate);

		when(personService.savePersonDTO(any(PersonDTO.class)))
				.thenThrow(new NotCreatedException("Backend Validation Errors", "", ""));

		String body = objectMapper.writeValueAsString(personDTO);

		this.mockMvc.perform(
				post("/person").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(body))
				.andExpect(status().isBadRequest());
	}

	@Test
	void getPersonWithCarsDTOById_200Ok() throws Exception {
		long id = 0;
		String name = "Test";
		LocalDate birthdate = LocalDate.now();
		List<CarDTO> cars = new ArrayList<>();
		CarDTO carDTO = new CarDTO();
		carDTO.setId((long) 0);
		carDTO.setModel("Test-Test");
		carDTO.setHorsepower(100);
		carDTO.setOwnerId((long) 0);
		cars.add(carDTO);

		PersonWithCarsDTO personWithCarsDTO = new PersonWithCarsDTO();
		personWithCarsDTO.setId(id);
		personWithCarsDTO.setName(name);
		personWithCarsDTO.setBirthdate(birthdate);
		personWithCarsDTO.setCars(cars);

		when(personService.getPersonWithCarsDTOById(anyLong())).thenReturn(personWithCarsDTO);

		this.mockMvc.perform(get("/personwithcars?personid=" + id)).andExpect(status().isOk());
	}

	@Test
	void getPersonWithCarsDTOById_400BadRequest() throws Exception {
		this.mockMvc.perform(get("/personwithcars?personid=")).andExpect(status().isBadRequest());
	}

	@Test
	void getPersonWithCarsDTOById_404NotFound() throws Exception {
		long id = 0;

		when(personService.getPersonWithCarsDTOById(anyLong()))
				.thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "\'personId=" + id + "' not found"));

		this.mockMvc.perform(get("/personwithcars?personid=" + id)).andExpect(status().isNotFound());
	}

	private PersonModel convertToPersonModel(PersonDTO personDTO) {
		return modelMapper.map(personDTO, PersonModel.class);
	}
}
