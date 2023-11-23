package ru.lanit.test.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.RequiredArgsConstructor;
import ru.lanit.test.models.CarModel;
import ru.lanit.test.models.PersonModel;
import ru.lanit.test.services.PersonService;

@Component
@RequiredArgsConstructor
public class CarModelValidator implements Validator {

	private final PersonService personService;

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
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			String formattedString = today.format(formatter);
			errors.rejectValue("ownerId", "", "Owner's birthdate should be before " + formattedString);
		}
	}

}
