package com.microservice.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.insurance.exception.InsuranceApplicationException;
import com.microservice.insurance.service.InsuranceService;
import com.microservice.insurance.util.ConstantUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/insurance")
public class InsuranceController {

	private final InsuranceService insuranceService;

	@Autowired
	public InsuranceController(InsuranceService insuranceService) {
		this.insuranceService = insuranceService;
	}

	@GetMapping("/check-eligibility")
	public ResponseEntity<String> checkUserEligibility(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
		boolean isEligible = insuranceService.isUserEligibleForInsurance(userId);
		if (!isEligible) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConstantUtil.USER_NOT_ELIGIBLE);
		}
		return ResponseEntity.status(HttpStatus.OK).body(ConstantUtil.USER_ELIGIBLE);
	}

	@PostMapping("/issue-insurance")
	public ResponseEntity<String> issueInsurance(HttpServletRequest request, @RequestParam Double amount)
			throws InsuranceApplicationException {
		Long userId = (Long) request.getAttribute("userId");
		String response = insuranceService.issueInsurance(userId, amount);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
