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
		long id = 0;
		String name = "Test";
		LocalDate birthdate = LocalDate.now();
		List<CarModel> cars = new ArrayList<>();
		CarModel carModel = new CarModel();
		carModel.setId((long) 0);
		carModel.setModel("Test-Test");
		carModel.setHorsepower(100);
		carModel.setOwner(personModel);
		cars.add(carModel);

		personModel.setId(id);
		personModel.setName(name);
		personModel.setBirthdate(birthdate);
		personModel.setCars(cars);

		assertEquals(id, personModel.getId());
		assertEquals(name, personModel.getName());
		assertEquals(birthdate, personModel.getBirthdate());
		assertEquals(cars, personModel.getCars());
	}
}
