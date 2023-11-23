package ru.lanit.test.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonDTOTest {
	private PersonDTO personDTO;

	@BeforeEach
	public void beforeEach() {
		personDTO = new PersonDTO();
	}

	@AfterEach
	public void afterEach() {
		personDTO = null;
	}

	@Test
	public void testGetterSetterAnnotation() {
		long id = 0;
		String name = "Test";
		LocalDate birthdate = LocalDate.now();

		personDTO.setId(id);
		personDTO.setName(name);
		personDTO.setBirthdate(birthdate);

		assertEquals(id, personDTO.getId());
		assertEquals(name, personDTO.getName());
		assertEquals(birthdate, personDTO.getBirthdate());
	}
}
