package com.microservice.insurance.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.insurance.client.UserClientHandler;
import com.microservice.insurance.dao.InsuranceRepository;
import com.microservice.insurance.entity.Insurance;
import com.microservice.insurance.exception.InsuranceApplicationException;
import com.microservice.insurance.service.InsuranceService;
import com.microservice.insurance.util.ConstantUtil;

@Service
public class InsuranceServiceImpl implements InsuranceService {
	@Value("${insurance_microservice.verify_user.url}")
	private String verifyUserUrl;

	@Autowired
	private UserClientHandler userClientHandler;

	@Autowired
	private InsuranceRepository insuranceRepository;

	@Override
	public boolean isUserEligibleForInsurance(Long userId) {
		return userClientHandler.verifyUser(userId);
	}

	@Override
	public String issueInsurance(Long userId, Double amount) throws InsuranceApplicationException {
		if (!isUserEligibleForInsurance(userId)) {
			throw new InsuranceApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_FOUND);
		}
		if (isUserAlreadyInsured(userId)) {
			throw new InsuranceApplicationException(HttpStatus.BAD_REQUEST, ConstantUtil.USER_ALREADY_INSURED);
		}
		// Logic to issue insurance
		Insurance insurance = new Insurance();
		insurance.setUserId(userId);
		insurance.setIssued(true);
		insurance.setAmount(amount);
		insuranceRepository.save(insurance);
		return ConstantUtil.USER_INSURED_SUCCESSFULLY;
	}

	private boolean isUserAlreadyInsured(Long userId) {
		// Logic to check if the user is already present in the insurance database
		Optional<Insurance> insurance = insuranceRepository.findByUserId(userId);
		return insurance.isPresent();
	}

}
