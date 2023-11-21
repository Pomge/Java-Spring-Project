package ru.lanit.test.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

public class PersonDTO {
	@NotNull(message = "Must not be null")
	private Long id;

	@NotNull(message = "Must not be null")
	private String name;

	@NotNull(message = "Must not be null")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@JsonFormat(pattern = "dd.MM.yyyy")
	private LocalDate birthdate;

	@OneToMany(mappedBy = "owner")
	private List<CarDTO> cars;

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

	public void setCars(List<CarDTO> cars) {
		this.cars = cars;
	}

	public List<CarDTO> getCars() {
		return cars;
	}

}
