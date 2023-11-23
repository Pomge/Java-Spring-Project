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
		PersonModel owner = new PersonModel();
		carModel.setId((long) 0);
		carModel.setModel("Test-Test");
		carModel.setHorsepower(100);
		carModel.setOwner(owner);

		assertEquals(0, carModel.getId());
		assertEquals("Test", carModel.getModel());
		assertEquals(100, carModel.getHorsepower());
		assertEquals(owner, carModel.getOwner());

		owner = null;
	}
}
