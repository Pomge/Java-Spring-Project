package ru.lanit.test.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarDTOTest {
	private CarDTO carDTO;

	@BeforeEach
	public void beforeEach() {
		carDTO = new CarDTO();
	}

	@AfterEach
	public void afterEach() {
		carDTO = null;
	}

	@Test
	public void testGetterSetterAnnotation() {
		long id = 0;
		String model = "Test-Test";
		int horsepower = 100;
		long ownerId = 0;

		carDTO.setId(id);
		carDTO.setModel(model);
		carDTO.setHorsepower(horsepower);
		carDTO.setOwnerId(ownerId);

		assertEquals(id, carDTO.getId());
		assertEquals(model, carDTO.getModel());
		assertEquals(horsepower, carDTO.getHorsepower());
		assertEquals(ownerId, carDTO.getOwnerId());
	}
}
