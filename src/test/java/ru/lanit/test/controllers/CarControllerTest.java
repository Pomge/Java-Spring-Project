package ru.lanit.test.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import ru.lanit.test.dto.CarDTO;
import ru.lanit.test.models.CarModel;
import ru.lanit.test.services.CarService;
import ru.lanit.test.util.NotCreatedException;

@AutoConfigureMockMvc
@WebMvcTest(controllers = CarController.class)
class CarControllerTest {
	@MockBean
	private CarService carService;

	private MockMvc mockMvc;
	private final ObjectMapper objectMapper;
	private final ModelMapper modelMapper;

	@Autowired
	public CarControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, ModelMapper modelMapper) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.modelMapper = modelMapper;
	}

	@BeforeEach
	public void beforeEach() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carService))
				.setControllerAdvice(new MyExceptionHandler()).build();
	}

	@Test
	void addCarDTO_200Ok() throws Exception {
		long id = 0;
		String model = "Test-Test";
		int horsepower = 100;
		long ownerId = 0;

		CarDTO carDTO = new CarDTO();
		carDTO.setId(id);
		carDTO.setModel(model);
		carDTO.setHorsepower(horsepower);
		carDTO.setOwnerId(ownerId);

		when(carService.saveCarDTO(any(CarDTO.class))).thenReturn(convertToCarModel(carDTO));

		String body = objectMapper.writeValueAsString(carDTO);

		this.mockMvc
				.perform(post("/car").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(body))
				.andExpect(status().isOk());
	}

	@Test
	void addCarDTO_400BadRequest() throws Exception {
		Long id = null;
		String model = null;
		Integer horsepower = null;
		Long ownerId = null;

		CarDTO carDTO = new CarDTO();
		carDTO.setId(id);
		carDTO.setModel(model);
		carDTO.setHorsepower(horsepower);
		carDTO.setOwnerId(ownerId);

		when(carService.saveCarDTO(any(CarDTO.class)))
				.thenThrow(new NotCreatedException("Backend Validation Errors", "", ""));

		String body = objectMapper.writeValueAsString(carDTO);

		this.mockMvc
				.perform(post("/car").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(body))
				.andExpect(status().isBadRequest());
	}

	private CarModel convertToCarModel(CarDTO carDTO) {
		return modelMapper.map(carDTO, CarModel.class);
	}
}
