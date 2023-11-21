package ru.lanit.test.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.lanit.test.models.CarModel;
import ru.lanit.test.models.PersonModel;
import ru.lanit.test.services.CarService;
import ru.lanit.test.services.PersonService;

@Component
public class CarValidator implements Validator {

	private final CarService carService;
	private final PersonService personService;

	@Autowired
	public CarValidator(CarService carService, PersonService personService) {
		this.carService = carService;
		this.personService = personService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return CarModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CarModel carModel = (CarModel) target;
		long carId = carModel.getId();

		Optional<CarModel> optionalCar = carService.findById(carId);
		if (optionalCar.isPresent()) {
			CarModel carModelFromDataBase = optionalCar.get();
			long dataBase_id = carModelFromDataBase.getId();

			if (carId == dataBase_id) {
				errors.rejectValue("id", "", "This \'id\' is already exist");
			}
		}

		long ownerId = carModel.getOwner().getId();
		Optional<PersonModel> optionalPerson = personService.findById(ownerId);
		if (!optionalPerson.isPresent()) {
			errors.rejectValue("ownerId", "", "This \'ownerId\' does not exist");
		} else {
			PersonModel owner = optionalPerson.get();
			LocalDate birthdate = owner.getBirthdate();
			LocalDate today = LocalDate.now();

			long yearsDifference = Period.between(birthdate, today).getYears();
			if (yearsDifference < 18) {
				today.minusYears(18);
				errors.rejectValue("ownerId", "", "Owner's birthdate should be before '" + today + "'");
			}
		}
	}
}
