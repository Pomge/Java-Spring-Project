package ru.lanit.test.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@Override
	public String toString() {
		return "PersonDTO [id=" + id + ", name=" + name + ", birthdate=" + birthdate + "]";
	}

}
