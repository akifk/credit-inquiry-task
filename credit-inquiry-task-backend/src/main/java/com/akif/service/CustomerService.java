package com.akif.service;

import com.akif.dto.CustomerDto;
import com.akif.response.Response;

import java.util.List;

public interface CustomerService {
    Response add(CustomerDto customerDto);

    Response update(CustomerDto customerDto);

    void delete(long id);
}
