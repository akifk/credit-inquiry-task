package com.akif.dto;

import com.akif.response.Response;
import lombok.Data;

@Data
public class CustomerDto implements Response {
    private long identityNumber;

    private String name;

    private String surName;

    private double monthlyIncome;

    private long telephone;
}
