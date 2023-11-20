package ru.lanit.test.util;

import java.util.List;
import java.util.Map;

public class NotCreatedErrorResponse {
	private final String message = "Validation failed";
	private Map<String, List<String>> errors;

	public NotCreatedErrorResponse(Map<String, List<String>> errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, List<String>> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, List<String>> errors) {
		this.errors = errors;
	}

}
