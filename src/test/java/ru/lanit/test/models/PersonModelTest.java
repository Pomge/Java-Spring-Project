package ru.lanit.test.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonModelTest {
	private PersonModel personModel;

	@BeforeEach
	public void beforeEach() {
		personModel = new PersonModel();
	}

	@AfterEach
	public void afterEach() {
		personModel = null;
	}

	@Test
	public void testGetterSetterAnnotation() {
		LocalDate now = LocalDate.now();

		List<CarModel> cars = new ArrayList<>();
		CarModel carModel = new CarModel();
		carModel.setId((long) 0);
		carModel.setModel("Test-Test");
		carModel.setHorsepower(100);
		carModel.setOwner(personModel);
		cars.add(carModel);

		personModel.setId((long) 0);
		personModel.setName("Test");
		personModel.setBirthdate(now);
		personModel.setCars(cars);

		assertEquals(0, personModel.getId());
		assertEquals("Test", personModel.getName());
		assertEquals(now, personModel.getBirthdate());
		assertEquals(cars, personModel.getCars());
	}
}
