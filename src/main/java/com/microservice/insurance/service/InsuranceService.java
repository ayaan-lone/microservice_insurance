package com.microservice.insurance.service;

import com.microservice.insurance.exception.InsuranceApplicationException;

public interface InsuranceService {

    boolean isUserEligibleForInsurance(Long userId);

    String issueInsurance(Long userId, Double amount) throws InsuranceApplicationException;

}
