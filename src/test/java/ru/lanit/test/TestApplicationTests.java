package ru.lanit.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {
	private final ModelMapper modelMapper;

	@Autowired
	public TestApplicationTests(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Test
	void contextLoads() {
		assertNotNull(modelMapper);
	}
}
