package ru.lanit.test.util;

import java.util.List;
import java.util.Map;

public class NotCreatedErrorResponse {
	private String message;
	private Map<String, List<String>> errors;

	public NotCreatedErrorResponse(String message, Map<String, List<String>> errors) {
		this.message = message;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, List<String>> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, List<String>> errors) {
		this.errors = errors;
	}

}
