package ru.lanit.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.FieldError;

public class NotCreatedException extends RuntimeException {

	private Map<String, List<String>> exceptions;

	public NotCreatedException(String errorMessage, List<FieldError> errors) {
		super(errorMessage);

		exceptions = new HashMap<>();
		for (FieldError error : errors) {
			String field = error.getField();
			String message = error.getDefaultMessage();
			this.addException(field, message);
		}
	}

	private void addException(String key, String message) {
		List<String> errorMessages = exceptions.get(key);

		if (errorMessages == null) {
			errorMessages = new ArrayList<>();
			errorMessages.add(message);
			exceptions.put(key, errorMessages);
		} else {
			errorMessages.add(message);
		}
	}

	public Map<String, List<String>> getExceptions() {
		return exceptions;
	}
}
