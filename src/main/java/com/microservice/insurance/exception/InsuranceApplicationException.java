package com.microservice.insurance.exception;

import org.springframework.http.HttpStatus;

public class InsuranceApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -61961329716931471L;
	private HttpStatus httpStatus;
	private String message;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public InsuranceApplicationException(HttpStatus httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}
}