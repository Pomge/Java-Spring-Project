package ru.lanit.test.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Car")
@Getter
@Setter
@NoArgsConstructor
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
}
