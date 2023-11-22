package ru.lanit.test.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.lanit.test.models.PersonModel;

@Component
public class PersonModelValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PersonModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PersonModel personModel = (PersonModel) target;

		LocalDate birthdateDate = personModel.getBirthdate();
		LocalDate today = LocalDate.now();

		if (birthdateDate.isAfter(today)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			String formattedString = today.format(formatter);
			errors.rejectValue("birthdate", "", "Must be before current date " + formattedString);
		}
	}
}
