package ru.lanit.test.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Car")
public class CarModel {
	@Id
	@Column(name = "id")
	@NotNull(message = "Must not be null")
	private Long id;

	@Column(name = "model")
	@NotNull(message = "Must not be null")
	@Pattern(regexp = "^[a-zA-Z0-9]+-[a-zA-Z0-9]+$", message = "Must be like \'VENDOR-MODELNAME\'")
	private String model;

	@Column(name = "horsepower")
	@NotNull(message = "Must not be null")
	@Min(value = 0, message = "Must be greater than 0")
	private Integer horsepower;

	@ManyToOne
	@JoinColumn(name = "ownerId", referencedColumnName = "id")
	private PersonModel owner;

	public CarModel() {

	}

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

	public PersonModel getOwner() {
		return owner;
	}

	public void setOwner(PersonModel owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "CarModel [id=" + id + ", model=" + model + ", horsepower=" + horsepower + ", owner=" + owner + "]";
	}

}
