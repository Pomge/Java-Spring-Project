package ru.lanit.test.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonWithCarsDTO {
	@NotNull(message = "Must not be null")
	private Long id;

	@NotNull(message = "Must not be null")
	private String name;

	@NotNull(message = "Must not be null")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@JsonFormat(pattern = "dd.MM.yyyy")
	private LocalDate birthdate;

	private List<CarDTO> cars;

	public PersonWithCarsDTO(PersonDTO personDTO, List<CarDTO> cars) {
		this.id = personDTO.getId();
		this.name = personDTO.getName();
		this.birthdate = personDTO.getBirthdate();
		this.cars = cars;
	}
}
