package com.akif.service.imp;

import com.akif.Util.Common;
import com.akif.dto.CreditDto;
import com.akif.entity.Credit;
import com.akif.entity.Customer;
import com.akif.enums.CreditStatusEnum;
import com.akif.repository.CreditRepository;
import com.akif.repository.CustomerRepository;
import com.akif.response.CreditStatusResponse;
import com.akif.response.ErrorResponse;
import com.akif.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CreditServiceImpTest {

    @InjectMocks
    private CreditServiceImp creditService;

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void getCreditStatusCase1Reject() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(0);
        credit.setCreditLimit(0);
        credit.setCreditStatus(CreditStatusEnum.Reject);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(100);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));
        Response result = creditService.getCreditStatus(customer.getIdentityNumber());

        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Reject);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), 0);
    }
    @Test
    void getCreditStatusCase2Reject() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(499);
        credit.setCreditStatus(CreditStatusEnum.Reject);
        credit.setCreditLimit(0);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(100);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Reject);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), 0);
    }
    @Test
    void getCreditStatusCase3Reject() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(-100);
        credit.setCreditStatus(CreditStatusEnum.Reject);
        credit.setCreditLimit(0);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(100);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Reject);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), 0);
    }
    @Test
    void getCreditStatusCase4Accept10000Limit() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(500);
        credit.setCreditStatus(CreditStatusEnum.Accept);
        credit.setCreditLimit(10000);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(100);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), 10000);
    }
    @Test
    void getCreditStatusCase5Accept10000Limit() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(999);
        credit.setCreditStatus(CreditStatusEnum.Accept);
        credit.setCreditLimit(10000);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(100);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), 10000);

    }
    @Test
    void getCreditStatusCase6AcceptCalc() {
        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(100);
        customer.setTelephone(5555555555L);

        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(1000);
        credit.setCreditStatus(CreditStatusEnum.Accept);
        double expectedResult = customer.getMonthlyIncome() * Common.getCreditLimitMultiplier();
        credit.setCreditLimit(expectedResult);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), expectedResult);
    }
    @Test
    void getCreditStatusCase7AcceptCalc() {
        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(100);
        customer.setTelephone(5555555555L);

        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(9999999);
        credit.setCreditStatus(CreditStatusEnum.Accept);
        double expectedResult = customer.getMonthlyIncome() * Common.getCreditLimitMultiplier();
        credit.setCreditLimit(expectedResult);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), expectedResult);
    }
    @Test
    void getCreditStatusCase8AcceptCalc() {
        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(0);
        customer.setTelephone(5555555555L);

        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(1000);
        double expectedResult = customer.getMonthlyIncome() * Common.getCreditLimitMultiplier();
        credit.setCreditLimit(expectedResult);
        credit.setCreditStatus(CreditStatusEnum.Accept);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), expectedResult);
    }
    @Test
    void getCreditStatusCase9AcceptCalc() {
        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(750);
        customer.setTelephone(5555555555L);

        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(1000);
        double expectedResult = customer.getMonthlyIncome() * Common.getCreditLimitMultiplier();
        credit.setCreditLimit(expectedResult);
        credit.setCreditStatus(CreditStatusEnum.Accept);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), expectedResult);
    }
    @Test
    void getCreditStatusCase10AcceptCalc() {
        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(8000);
        customer.setTelephone(5555555555L);

        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(1000);
        double expectedResult = customer.getMonthlyIncome() * Common.getCreditLimitMultiplier();
        credit.setCreditLimit(expectedResult);
        credit.setCreditStatus(CreditStatusEnum.Accept);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), expectedResult);
    }
    @Test
    void getCreditStatusCase11Accept10000Limit() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(750);
        credit.setCreditLimit(10000);
        credit.setCreditStatus(CreditStatusEnum.Accept);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(3000);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), 10000);
    }
    @Test
    void getCreditStatusCase12Accept10000Limit() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(750);
        credit.setCreditLimit(10000);
        credit.setCreditStatus(CreditStatusEnum.Accept);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(4999);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), 10000);

    }
    @Test
    void getCreditStatusCase13Accept10000Limit() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(750);
        credit.setCreditLimit(10000);
        credit.setCreditStatus(CreditStatusEnum.Accept);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(0);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), 10000);
    }
    @Test
    void getCreditStatusCase14Accept20000Limit() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(750);
        credit.setCreditLimit(20000);
        credit.setCreditStatus(CreditStatusEnum.Accept);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(5000);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), 20000);
    }
    @Test
    void getCreditStatusCase15Accept20000Limit() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(750);
        credit.setCreditLimit(20000);
        credit.setCreditStatus(CreditStatusEnum.Accept);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(7000);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));

        Response result = creditService.getCreditStatus(customer.getIdentityNumber());
        assertEquals(result.getClass(), CreditStatusResponse.class);
        assertEquals(((CreditStatusResponse) result).getCreditStatusEnum(), CreditStatusEnum.Accept);
        assertEquals(((CreditStatusResponse) result).getCreditLimit(), 20000);
    }
    @Test
    void getCreditStatusErrorResponseCase1() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(0);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(100);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.of(credit));
        Response result = creditService.getCreditStatus(customer.getIdentityNumber());

        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getErrorCode(), 2);
    }
    @Test
    void getCreditStatusErrorResponseCase2() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(0);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(100);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(creditRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Response result = creditService.getCreditStatus(customer.getIdentityNumber());

        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getErrorCode(), 2);
    }
    @Test
    void getCreditStatusErrorResponseCase3() {
        Credit credit = new Credit();
        credit.setIdentityNumber(123);
        Common.setCreditScore(0);

        Customer customer = new Customer();
        customer.setIdentityNumber(10000000000L);
        customer.setName("aa");
        customer.setSurName("bb");
        customer.setMonthlyIncome(100);
        customer.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Response result = creditService.getCreditStatus(customer.getIdentityNumber());

        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getErrorCode(), 2);
    }
}