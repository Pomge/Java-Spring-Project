package ru.lanit.test.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Car")
public class CarModel {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "model")
	private String model;

	@Column(name = "horsepower")
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

}
