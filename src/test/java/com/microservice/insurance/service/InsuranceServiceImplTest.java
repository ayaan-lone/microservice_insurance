package com.microservice.insurance.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservice.insurance.client.UserClientHandler;
import com.microservice.insurance.dao.InsuranceRepository;
import com.microservice.insurance.entity.Insurance;
import com.microservice.insurance.exception.InsuranceApplicationException;
import com.microservice.insurance.service.impl.InsuranceServiceImpl;
import com.microservice.insurance.util.ConstantUtil;

@ExtendWith(MockitoExtension.class)
public class InsuranceServiceImplTest {

    @InjectMocks
    private InsuranceServiceImpl insuranceService;

    @Mock
    private UserClientHandler userClientHandler;

    @Mock
    private InsuranceRepository insuranceRepository;

    @BeforeEach
    void setUp() throws Exception {
        // Use reflection to set the value of verifyUserUrl
        Field verifyUserUrlField = InsuranceServiceImpl.class.getDeclaredField("verifyUserUrl");
        verifyUserUrlField.setAccessible(true);
        verifyUserUrlField.set(insuranceService, "http://example.com/verify-user");
    }

    @Test
    void testIsUserEligibleForInsurance_UserIsEligible() {
        when(insuranceRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
        when(userClientHandler.verifyUser(anyLong())).thenReturn(true);

        boolean result = insuranceService.isUserEligibleForInsurance(1L);
        assertTrue(result);
    }

    @Test
    void testIsUserEligibleForInsurance_UserAlreadyInsured() {
        Insurance insurance = new Insurance();
        insurance.setUserId(1L);
        when(insuranceRepository.findByUserId(anyLong())).thenReturn(Optional.of(insurance));

        boolean result = insuranceService.isUserEligibleForInsurance(1L);
        assertFalse(result);
    }

    @Test
    void testIsUserEligibleForInsurance_UserNotVerified() {
        when(insuranceRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
        when(userClientHandler.verifyUser(anyLong())).thenReturn(false);

        boolean result = insuranceService.isUserEligibleForInsurance(1L);
        assertFalse(result);
    }

    @Test
    void testIssueInsurance_UserNotEligible() {
        when(insuranceRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
        when(userClientHandler.verifyUser(anyLong())).thenReturn(false);

        InsuranceApplicationException exception = assertThrows(InsuranceApplicationException.class, () -> {
            insuranceService.issueInsurance(1L, 1000.0);
        });

        assertTrue(exception.getMessage().contains(ConstantUtil.USER_NOT_ELIGIBLE));
    }

    @Test
    void testIssueInsurance_UserEligible() throws InsuranceApplicationException {
        when(insuranceRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
        when(userClientHandler.verifyUser(anyLong())).thenReturn(true);

        String result = insuranceService.issueInsurance(1L, 1000.0);

        assertTrue(result.contains(ConstantUtil.USER_INSURED_SUCCESSFULLY));
    }
}
