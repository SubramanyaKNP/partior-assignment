package com.partior.starship.exceptions.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalErrorHandler implements ErrorController {

	private static final Logger logger = LogManager.getLogger(GlobalErrorHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<String> handleException(Exception ex) {
		// Log the exception to backend logs
		logger.error("An unexpected error occurred", ex);
		// Return appropriate error response to client without exposing stack trace
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("OOPS!! An unexpected error occurred!!. Please contact ");
	}

}
