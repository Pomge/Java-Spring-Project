package ru.lanit.test.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarDTO {
	@NotNull(message = "Must not be null")
	private Long id;

	@NotNull(message = "Must not be null")
	@Pattern(regexp = "^[a-zA-Z0-9]+-[a-zA-Z0-9]+$", message = "Must be like \'VENDOR-MODELNAME\'")
	private String model;

	@NotNull(message = "Must not be null")
	@Min(value = 1, message = "Must be greater than 0")
	private Integer horsepower;

	@NotNull(message = "Must not be null")
	private Long ownerId;
}
