package com.microservice.insurance.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InsuranceApplicationExceptionHandler {

	@ExceptionHandler(value = { InsuranceApplicationException.class })
	ResponseEntity<Object> handleTransactionException(InsuranceApplicationException insuranceApplicationException) {
		return ResponseEntity.status(insuranceApplicationException.getHttpStatus())
				.body(insuranceApplicationException.getMessage());
	}
}
