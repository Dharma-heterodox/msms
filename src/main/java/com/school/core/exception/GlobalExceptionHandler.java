package com.school.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * <p>
 * Handle global exceptions.
 * </p>
 * 
 * @author Rajasankar.s
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String ERROR = "Oops!";

	@ExceptionHandler(value = CustomValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleBaseException(Exception ex) {
		log.error(ERROR, ex);
		CustomValidationException validationException = (CustomValidationException) ex;
		ErrorHandle errorHandle = validationException.getErrorCode();
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(errorHandle.getErrorCode());
		errorMessage.setMessage(errorHandle.getMessage());
		return errorMessage;
	}

	@ExceptionHandler({ Exception.class, RuntimeException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleRuntimeException(Exception ex) {
		log.error(ERROR, ex);
		ErrorHandle errorHandle = ErrorCode.SCHOOL_MANAGEMENT_2001;
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(errorHandle.getErrorCode());
		errorMessage.setMessage(ex.getMessage());
		return errorMessage;
	}

}
