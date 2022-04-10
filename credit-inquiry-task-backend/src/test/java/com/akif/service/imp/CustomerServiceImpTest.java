package com.akif.service.imp;

import com.akif.dto.CustomerDto;
import com.akif.entity.Customer;
import com.akif.repository.CustomerRepository;
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
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImpTest {
    @InjectMocks
    private CustomerServiceImp customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void addCaseValid1() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(10000000000L);
        customerDto.setName("aa");
        customerDto.setSurName("bb");
        customerDto.setMonthlyIncome(100);
        customerDto.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(mock(Customer.class));
        Response result = customerService.add(customerDto);

        assertEquals(result.getClass(), CustomerDto.class);
        assertEquals(((CustomerDto) result).getIdentityNumber(), customerDto.getIdentityNumber());
        assertEquals(((CustomerDto) result).getName(), customerDto.getName());
        assertEquals(((CustomerDto) result).getSurName(), customerDto.getSurName());
        assertEquals(((CustomerDto) result).getMonthlyIncome(), customerDto.getMonthlyIncome());
        assertEquals(((CustomerDto) result).getTelephone(), customerDto.getTelephone());
    }

    @Test
    void addErrorResponse1() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(10000000000L);
        customerDto.setName("aa");
        customerDto.setSurName("bb");
        customerDto.setMonthlyIncome(100);
        customerDto.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Customer.class)));

        Response result = customerService.add(customerDto);
        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getErrorCode(), 3);
        assertEquals(((ErrorResponse) result).getMessage(), "this customer is already added");
    }

    @Test
    void addCaseErrorResponse2() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(10);
        customerDto.setName("aa");
        customerDto.setSurName("bb");
        customerDto.setMonthlyIncome(100);
        customerDto.setTelephone(5555555555L);

        Response result = customerService.add(customerDto);

        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getMessage(), "customer info not valid");
        assertEquals(((ErrorResponse) result).getErrorCode(), 6);
    }

    @Test
    void addCaseErrorResponse3() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(10000000000L);
        customerDto.setName("aa");
        customerDto.setSurName("bb");
        customerDto.setMonthlyIncome(100);
        customerDto.setTelephone(55);

        Response result = customerService.add(customerDto);

        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getMessage(), "customer info not valid");
        assertEquals(((ErrorResponse) result).getErrorCode(), 6);
    }

    @Test
    void addCaseErrorResponse4() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(-50);
        customerDto.setName("aa");
        customerDto.setSurName("bb");
        customerDto.setMonthlyIncome(500);
        customerDto.setTelephone(5555555555L);

        Response result = customerService.add(customerDto);

        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getMessage(), "customer info not valid");
        assertEquals(((ErrorResponse) result).getErrorCode(), 6);
    }

    @Test
    void addCaseErrorResponse5() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(10000000000L);
        customerDto.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        customerDto.setSurName("bb");
        customerDto.setMonthlyIncome(500);
        customerDto.setTelephone(5555555555L);

        Response result = customerService.add(customerDto);

        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getMessage(), "customer info not valid");
        assertEquals(((ErrorResponse) result).getErrorCode(), 6);
    }

    @Test
    void addCaseErrorResponse6() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(10000000000L);
        customerDto.setName("aa");
        customerDto.setSurName("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        customerDto.setMonthlyIncome(500);
        customerDto.setTelephone(5555555555L);

        Response result = customerService.add(customerDto);

        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getMessage(), "customer info not valid");
        assertEquals(((ErrorResponse) result).getErrorCode(), 6);
    }

    @Test
    void addCaseErrorResponse7() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(10000000000L);
        customerDto.setName("aa");
        customerDto.setSurName("bb");
        customerDto.setMonthlyIncome(500);
        customerDto.setTelephone(555555555555555L);

        Response result = customerService.add(customerDto);

        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getMessage(), "customer info not valid");
        assertEquals(((ErrorResponse) result).getErrorCode(), 6);
    }

    @Test
    void addCaseErrorResponse8() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(100000000000000000L);
        customerDto.setName("aa");
        customerDto.setSurName("bb");
        customerDto.setMonthlyIncome(500);
        customerDto.setTelephone(5555555555L);

        Response result = customerService.add(customerDto);

        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getMessage(), "customer info not valid");
        assertEquals(((ErrorResponse) result).getErrorCode(), 6);
    }


    @Test
    void update() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(10000000000L);
        customerDto.setName("aa");
        customerDto.setSurName("bb");
        customerDto.setMonthlyIncome(100);
        customerDto.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Customer.class)));
        when(customerRepository.save(any(Customer.class))).thenReturn(mock(Customer.class));
        Response result = customerService.update(customerDto);

        assertEquals(result.getClass(), CustomerDto.class);
        assertEquals(((CustomerDto) result).getIdentityNumber(), customerDto.getIdentityNumber());
        assertEquals(((CustomerDto) result).getName(), customerDto.getName());
        assertEquals(((CustomerDto) result).getSurName(), customerDto.getSurName());
        assertEquals(((CustomerDto) result).getMonthlyIncome(), customerDto.getMonthlyIncome());
        assertEquals(((CustomerDto) result).getTelephone(), customerDto.getTelephone());
    }

    @Test
    void updateErrorResponse() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdentityNumber(10000000000L);
        customerDto.setName("aa");
        customerDto.setSurName("bb");
        customerDto.setMonthlyIncome(100);
        customerDto.setTelephone(5555555555L);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Response result = customerService.update(customerDto);
        assertEquals(result.getClass(), ErrorResponse.class);
        assertEquals(((ErrorResponse) result).getErrorCode(), 5);
        assertEquals(((ErrorResponse) result).getMessage(), "this customer not found");
    }

    @Test
    void delete() {
        long identityNumber = 150;
        customerService.delete(identityNumber);
    }
}