package ru.lanit.test.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
			Calendar birthdate = getCalendar(owner.getBirthdate());
			Calendar today = getCalendar(new Date());

			int yearsDifference = today.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR);
			if (birthdate.get(Calendar.MONTH) > today.get(Calendar.MONTH)
					|| (birthdate.get(Calendar.MONTH) == today.get(Calendar.MONTH)
							&& birthdate.get(Calendar.DATE) > today.get(Calendar.DATE))) {
				yearsDifference -= 1;
			}

			if (yearsDifference < 18) {
				today.add(Calendar.YEAR, -18);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				String formatedDate = dateFormat.format(today.getTime());
				errors.rejectValue("ownerId", "", "Owner's birthdate should be before " + formatedDate);
			}
		}
	}

	private static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
}
