package sbnz.ftn.uns.ac.rs.cdss.exceptions;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { NotValidParamsException.class })
	protected ResponseEntity<Object> quantitynotZeroHandle(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Not valid params");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(value = { ValidationFormException.class })
	protected ResponseEntity<Object> notValidFormParams(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Validation error in form");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(value = { AlreadyExistsException.class })
	protected ResponseEntity<Object> entityAlreadyExists(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Entity already exists");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(value = { NotExistsException.class })
	protected ResponseEntity<Object> entityDoesNotExist(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Entity does not exist");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(value = { NotUniqueException.class })
	protected ResponseEntity<Object> entityIsNotUnique(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Entity is not unique");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(value = { FailedToCreateException.class })
	protected ResponseEntity<Object> entityCreationFailed(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Entity creation failed");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

}
