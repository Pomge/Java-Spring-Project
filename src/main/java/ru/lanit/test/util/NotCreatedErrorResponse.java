package ru.lanit.test.util;

import java.util.List;
import java.util.Map;

public class NotCreatedErrorResponse {
	private String message;
	private Map<String, List<String>> errors;

	public NotCreatedErrorResponse(NotCreatedException notCreatedException) {
		this.message = notCreatedException.getMessage();
		this.errors = notCreatedException.getExceptions();
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
