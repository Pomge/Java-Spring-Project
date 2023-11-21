package ru.lanit.test.util;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.lanit.test.models.PersonModel;
import ru.lanit.test.services.PersonService;

@Component
public class PersonValidator implements Validator {
	private final PersonService personService;

	@Autowired
	public PersonValidator(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return PersonModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PersonModel personModel = (PersonModel) target;
		long id = personModel.getId();

		Optional<PersonModel> optional = personService.findById(id);
		if (optional.isPresent()) {
			PersonModel personModelFromDataBase = optional.get();
			long dataBase_id = personModelFromDataBase.getId();

			if (id == dataBase_id) {
				errors.rejectValue("id", "", "This \'id\' is already exist");
			}
		}

		LocalDate birthdateDate = personModel.getBirthdate();
		LocalDate today = LocalDate.now();

		if (birthdateDate.isAfter(today)) {
			errors.rejectValue("birthdate", "", "Must be before current date '" + today + "'");
		}
	}
}
