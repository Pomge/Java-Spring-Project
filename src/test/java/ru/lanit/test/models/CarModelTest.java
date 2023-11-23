package ru.lanit.test.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarModelTest {
	private CarModel carModel;

	@BeforeEach
	public void beforeEach() {
		carModel = new CarModel();
	}

	@AfterEach
	public void afterEach() {
		carModel = null;
	}

	@Test
	public void testGetterSetterAnnotation() {
		long id = 0;
		String model = "Test-Test";
		int horsepower = 100;
		PersonModel owner = new PersonModel();

		carModel.setId(id);
		carModel.setModel(model);
		carModel.setHorsepower(horsepower);
		carModel.setOwner(owner);

		assertEquals(id, carModel.getId());
		assertEquals(model, carModel.getModel());
		assertEquals(horsepower, carModel.getHorsepower());
		assertEquals(owner, carModel.getOwner());
	}
}
