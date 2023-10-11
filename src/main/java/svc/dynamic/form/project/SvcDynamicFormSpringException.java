package svc.dynamic.form.project;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import svc.dynamic.form.project.Component.ResponseErrorComponent;
import svc.dynamic.form.project.Exception.NoSuchElementFoundException;
import svc.dynamic.form.project.Exception.ResourceNotFoundException;

/**
 *
 * @author ibent95
 */
@RestControllerAdvice
public class SvcDynamicFormSpringException extends ResponseEntityExceptionHandler {

	public static final String TRACE = "trace";

	@Value("${reflectoring.trace:false}")
	private boolean printStackTrace;

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseErrorComponent resourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
		return new ResponseErrorComponent(
			HttpStatus.NOT_FOUND.value(),
			new Date(),
			exception.getMessage(),
			request.getDescription(false));
	}

	// @ExceptionHandler(MethodArgumentNotValidException.class)
	// @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	// protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	// 	ResponseErrorComponent errorResponse = new ResponseErrorComponent(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error: " + ex.getMessage() + ". Check 'errors' field for details.");
	// 	// "Validation error. Check 'errors' field for details."

	// 	for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
	// 		errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
	// 	}

	// 	return ResponseEntity.unprocessableEntity().body(errorResponse);
	// }

	@ExceptionHandler(NoSuchElementFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementFoundException itemNotFoundException, WebRequest request) {
		// log.error("Failed to find the requested element", itemNotFoundException);
		return buildErrorResponse(itemNotFoundException, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(JsonMappingException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleJsonMappingException(JsonMappingException exception, WebRequest request) {
		// log.error("Unknown error occurred", exception);
		return buildErrorResponse(exception, "JSON processing error occurred: " + exception.getMessage() + ".", HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(JsonProcessingException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException exception, WebRequest request) {
		// log.error("Unknown error occurred", exception);
		return buildErrorResponse(exception, "JSON processing error occurred: " + exception.getMessage() + ".", HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
		// log.error("Unknown error occurred", exception);
		return buildErrorResponse(exception, "Unknown error occurred: " + exception.getMessage() + ".", HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	private ResponseEntity<Object> buildErrorResponse(Exception exception,
		HttpStatus httpStatus,
		WebRequest request) {
		return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
	}

	private ResponseEntity<Object> buildErrorResponse(Exception exception,
		String message,
		HttpStatus httpStatus,
		WebRequest request) {
		ResponseErrorComponent errorResponse = new ResponseErrorComponent(httpStatus.value(), message);

		if (printStackTrace && isTraceOn(request)) {
			errorResponse.setStackTrace(exception.getStackTrace());
		}

		return ResponseEntity.status(httpStatus).body(errorResponse);
	}

	private boolean isTraceOn(WebRequest request) {
		String[] value = request.getParameterValues(TRACE);
		return Objects.nonNull(value)
			&& value.length > 0
			&& value[0].contentEquals("true");
	}

	public ResponseEntity<Object> handleExceptionInternal(
		Exception ex,
		Object body,
		HttpHeaders headers,
		HttpStatus status,
		WebRequest request) {

		return buildErrorResponse(ex, status, request);
	}

}
