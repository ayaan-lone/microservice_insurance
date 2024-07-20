package com.microservice.insurance.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class InsuranceApplicationExceptionHandler {

	@ExceptionHandler(value = { InsuranceApplicationException.class })
	ResponseEntity<Object> handleTransactionException(InsuranceApplicationException insuranceApplicationException) {
		return ResponseEntity.status(insuranceApplicationException.getHttpStatus())
				.body(insuranceApplicationException.getMessage());
	}
	
	@ExceptionHandler(value = { HttpClientErrorException.class })
	ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException httpClientErrorException) {
		return ResponseEntity.status(httpClientErrorException.getStatusCode())
				.body(httpClientErrorException.getMessage());
	}
}
