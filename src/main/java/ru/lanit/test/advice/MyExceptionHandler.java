package ru.lanit.test.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import ru.lanit.test.util.NotCreatedErrorResponse;
import ru.lanit.test.util.NotCreatedException;

@RestControllerAdvice
public class MyExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<NotCreatedErrorResponse> handleException(NotCreatedException notCreatedException) {
		NotCreatedErrorResponse notCreatedErrorResponse = new NotCreatedErrorResponse(notCreatedException.getMessage(),
				notCreatedException.getExceptions());
		return new ResponseEntity<>(notCreatedErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleException(HttpMessageNotReadableException ex) {
		Map<String, String> exception = new HashMap<>();
		exception.put("message", "Malformed JSON Request");
		exception.put("debugMessage", ex.getMessage());
		return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseStatusException handleException(ResponseStatusException responseStatusException) {
		return responseStatusException;
	}
}