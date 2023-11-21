package ru.lanit.test.dto;

import java.time.LocalDate;
import java.util.List;

public class PersonWithCarsDTO {
	private Long id;
	private String name;
	private LocalDate birthdate;
	private List<CarDTO> cars;

	public PersonWithCarsDTO(PersonDTO personDTO, List<CarDTO> cars) {
		this.id = personDTO.getId();
		this.name = personDTO.getName();
		this.birthdate = personDTO.getBirthdate();
		this.cars = cars;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public List<CarDTO> getCars() {
		return cars;
	}

	public void setCars(List<CarDTO> cars) {
		this.cars = cars;
	}

}
