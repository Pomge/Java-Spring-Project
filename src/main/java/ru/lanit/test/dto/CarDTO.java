package ru.lanit.test.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getHorsepower() {
		return horsepower;
	}

	public void setHorsepower(Integer horsepower) {
		this.horsepower = horsepower;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

}
