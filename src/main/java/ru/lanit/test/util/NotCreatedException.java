package ru.lanit.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

public class NotCreatedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Map<String, List<String>> exceptions;

	public NotCreatedException(String message, MapBindingResult mapBindingResult) {
		super(message);
		this.exceptions = new HashMap<>();

		for (FieldError fieldError : mapBindingResult.getFieldErrors()) {
			String key = fieldError.getField();
			String validationMessage = fieldError.getDefaultMessage();
			this.addException(key, validationMessage);
		}
	}

	public NotCreatedException(String message, String field, String exception) {
		super(message);
		this.exceptions = new HashMap<>();
		this.exceptions.put(field, Arrays.asList(exception));
	}

	public <T> NotCreatedException(String message, ConstraintViolationException ex) {
		super(message);
		this.exceptions = new HashMap<>();

		for (ConstraintViolation<?> exeption : ex.getConstraintViolations()) {
			String key = exeption.getPropertyPath().toString();
			key = key.substring(key.lastIndexOf('.') + 1);
			String validationMessage = exeption.getMessage();
			this.addException(key, validationMessage);
		}
	}

	public void addException(String key, String message) {
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
