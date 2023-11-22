package ru.lanit.test.util;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.lanit.test.models.CarModel;
import ru.lanit.test.models.PersonModel;
import ru.lanit.test.services.PersonService;

@Component
public class CarModelValidator implements Validator {

	private final PersonService personService;

	@Autowired
	public CarModelValidator(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return CarModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CarModel carModel = (CarModel) target;

		long ownerId = carModel.getOwner().getId();
		PersonModel owner = personService.getPersonModelById(ownerId);
		LocalDate birthdate = owner.getBirthdate();
		LocalDate today = LocalDate.now();

		long yearsDifference = Period.between(birthdate, today).getYears();
		if (yearsDifference < 18) {
			today.minusYears(18);
			errors.rejectValue("ownerId", "", "Owner's birthdate should be before " + today);
		}
	}

}
