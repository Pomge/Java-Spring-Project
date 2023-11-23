package ru.lanit.test.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonWithCarsDTOTest {
	private PersonWithCarsDTO personWithCarsDTO;

	@BeforeEach
	public void beforeEach() {
		personWithCarsDTO = new PersonWithCarsDTO();
	}

	@AfterEach
	public void afterEach() {
		personWithCarsDTO = null;
	}

	@Test
	public void testGetterSetterAnnotation() {
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

		personWithCarsDTO.setId(id);
		personWithCarsDTO.setName(name);
		personWithCarsDTO.setBirthdate(birthdate);
		personWithCarsDTO.setCars(cars);

		assertEquals(id, personWithCarsDTO.getId());
		assertEquals(name, personWithCarsDTO.getName());
		assertEquals(birthdate, personWithCarsDTO.getBirthdate());
		assertEquals(cars, personWithCarsDTO.getCars());
	}

	@Test
	public void testDataAnnotationForNoArgsConstructor() {
		personWithCarsDTO = new PersonWithCarsDTO();
		assertNotNull(personWithCarsDTO);
	}
}
