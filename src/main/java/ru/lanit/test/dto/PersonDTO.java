package ru.lanit.test.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PersonDTO {
	@NotNull(message = "Must not be null")
	private Long id;

	@NotNull(message = "Must not be null")
	private String name;

	@NotNull(message = "Must not be null")
	@Pattern(regexp = "^[0-9]{1,2}.[0-9]{1,2}.[0-9]{4}$", message = "Must be in 'dd.MM.yyyy' format")
	private String birthdate;

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

	public Date getBirthdate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		Date date = null;
		try {
			date = format.parse(birthdate + " 12:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public void setCars(List<CarDTO> cars) {
		this.cars = cars;
	}

	public List<CarDTO> getCars() {
		return cars;
	}

}
