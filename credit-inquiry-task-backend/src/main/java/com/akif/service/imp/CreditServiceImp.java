package com.akif.service.imp;

import com.akif.Util.Common;
import com.akif.Util.Lggr;
import com.akif.dto.CustomerDto;
import com.akif.entity.Credit;
import com.akif.entity.Customer;
import com.akif.enums.CreditStatusEnum;
import com.akif.response.CreditStatusResponse;
import com.akif.repository.CreditRepository;
import com.akif.repository.CustomerRepository;
import com.akif.response.ErrorResponse;
import com.akif.response.Response;
import com.akif.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditServiceImp implements CreditService {
    private final CreditRepository creditRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Response getCreditStatus(long id) {
        Optional<Customer> customerResult = customerRepository.findById(id);
        Optional<Credit> creditResult = creditRepository.findById(id);
        if (!creditResult.isPresent() || !customerResult.isPresent()) {
            return new ErrorResponse("credit application not found for the identity number", 2);
        }
        return new CreditStatusResponse(creditResult.get().getCreditStatus(), creditResult.get().getCreditLimit());
    }

    @Override
    public Response applyForCredit(CustomerDto customerDto) {
        if (!Common.checkCustomerInfo(customerDto)) {
            Lggr.getInstance().info("CreditService checkCreditStatus Response: customer info not valid");
            return new ErrorResponse("customer info not valid", 6);
        }
        Optional<Customer> optionalCustomer = customerRepository.findById(customerDto.getIdentityNumber());
        Customer customer;
        if (!optionalCustomer.isPresent()) {
            customer = new Customer();
            customer.setIdentityNumber(customerDto.getIdentityNumber());
            customer.setName(customerDto.getName());
            customer.setSurName(customerDto.getSurName());
            customer.setMonthlyIncome(customerDto.getMonthlyIncome());
            customer.setTelephone(customerDto.getTelephone());
            customerRepository.save(customer);
        } else {
            customer = optionalCustomer.get();
        }
        Lggr.getInstance().info("CreditService checkCreditStatus Response: ok");
        return checkCreditStatus(customer);
    }

    private CreditStatusResponse saveNewCredit(Customer customer, CreditStatusEnum creditStatus, double creditLimit) {
        Credit credit = new Credit();
        credit.setIdentityNumber(customer.getIdentityNumber());
        credit.setCreditLimit(creditLimit);
        credit.setCustomer(customer);
        credit.setCreditStatus(creditStatus);
        creditRepository.save(credit);
        Common.sendSms(customer, creditStatus, creditLimit);
        return new CreditStatusResponse(creditStatus, creditLimit);
    }

    private CreditStatusResponse checkCreditStatus(Customer customer) {
        if (Common.getCreditScore(customer) < 500) {
            return new CreditStatusResponse(CreditStatusEnum.Reject, 0);
        } else if (Common.getCreditScore(customer) < 1000 && customer.getMonthlyIncome() < 5000) {
            return saveNewCredit(customer, CreditStatusEnum.Accept, 10000);
        } else if (Common.getCreditScore(customer) < 1000 && customer.getMonthlyIncome() >= 5000) {
            return saveNewCredit(customer, CreditStatusEnum.Accept, 20000);
        } else {
            double newCreditLimit = customer.getMonthlyIncome() * Common.getCreditLimitMultiplier();
            return saveNewCredit(customer, CreditStatusEnum.Accept, newCreditLimit);
        }
    }
}
