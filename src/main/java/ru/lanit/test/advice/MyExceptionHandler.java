package ru.lanit.test.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolationException;
import ru.lanit.test.util.NotCreatedErrorResponse;
import ru.lanit.test.util.NotCreatedException;

@RestControllerAdvice
public class MyExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<NotCreatedErrorResponse> handleException(NotCreatedException notCreatedException) {
		NotCreatedErrorResponse notCreatedErrorResponse = new NotCreatedErrorResponse(notCreatedException);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notCreatedErrorResponse);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleException(HttpMessageNotReadableException httpMessageNotReadableException) {
		Map<String, String> exception = new HashMap<>();
		exception.put("message", "Malformed JSON Request");
		exception.put("debugMessage", httpMessageNotReadableException.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
	}

	@ExceptionHandler
	public ResponseEntity<NotCreatedErrorResponse> handleException(
			ConstraintViolationException constraintViolationException) {
		NotCreatedException notCreatedException = new NotCreatedException("Frontend Validation Failed",
				constraintViolationException);
		NotCreatedErrorResponse notCreatedErrorResponse = new NotCreatedErrorResponse(notCreatedException);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notCreatedErrorResponse);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleException(ResponseStatusException responseStatusException) {
		return ResponseEntity.status(responseStatusException.getStatusCode()).body(responseStatusException.getBody());
	}
}