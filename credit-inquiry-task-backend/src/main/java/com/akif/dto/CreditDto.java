package com.akif.dto;

import com.akif.entity.Credit;
import com.akif.entity.Customer;
import com.akif.enums.CreditStatusEnum;
import com.akif.response.Response;
import lombok.Data;

@Data
public class CreditDto implements Response {
    private long identityNumber;

    private double creditAmount;

    private CreditStatusEnum creditStatus;
}
