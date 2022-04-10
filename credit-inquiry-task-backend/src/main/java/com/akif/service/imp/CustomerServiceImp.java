package com.akif.service.imp;

import com.akif.Util.Common;
import com.akif.Util.Lggr;
import com.akif.Util.Util;
import com.akif.dto.CustomerDto;
import com.akif.entity.Customer;
import com.akif.repository.CustomerRepository;
import com.akif.response.ErrorResponse;
import com.akif.response.Response;
import com.akif.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Response add(CustomerDto customerDto) {
        if (!Common.checkCustomerInfo(customerDto)) {
            Lggr.getInstance().info("CustomerService add Response: customer info not valid");
            return new ErrorResponse("customer info not valid", 6);
        }
        if (!customerRepository.findById(customerDto.getIdentityNumber()).isPresent()) {
            Customer customer = new Customer();
            customer.setIdentityNumber(customerDto.getIdentityNumber());
            customer.setName(customerDto.getName());
            customer.setSurName(customerDto.getSurName());
            customer.setMonthlyIncome(customerDto.getMonthlyIncome());
            customer.setTelephone(customerDto.getTelephone());
            customerRepository.save(customer);
            Lggr.getInstance().info("CustomerService add Response: ok");
            return customerDto;
        } else {
            Lggr.getInstance().info("CustomerService add Response: this customer is already added");
            return new ErrorResponse("this customer is already added", 3);
        }
    }

    @Override
    public Response update(CustomerDto customerDto) {
        Optional<Customer> result = customerRepository.findById(customerDto.getIdentityNumber());
        if (result.isPresent()) {
            Customer customer = result.get();
            customer.setName(customerDto.getName());
            customer.setSurName(customerDto.getSurName());
            customer.setMonthlyIncome(customerDto.getMonthlyIncome());
            customer.setTelephone(customerDto.getTelephone());
            final Customer customerDB = customerRepository.save(customer);
            Lggr.getInstance().info("CustomerService update Response: ok");
            return customerDto;
        } else {
            Lggr.getInstance().info("CustomerService update Response: this customer not found");
            return new ErrorResponse("this customer not found", 5);
        }
    }

    @Override
    public void delete(long id) {
        Lggr.getInstance().info("CustomerService delete Response: ok");
        customerRepository.deleteById(id);
    }

}
