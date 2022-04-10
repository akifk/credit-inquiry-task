package com.akif.service;

import com.akif.dto.CreditDto;
import com.akif.dto.CustomerDto;
import com.akif.response.Response;

import java.util.List;

public interface CreditService {

    Response getCreditStatus(long id);

    Response applyForCredit(CustomerDto customerDto);
}
