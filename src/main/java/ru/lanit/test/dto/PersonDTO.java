package ru.lanit.test.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
