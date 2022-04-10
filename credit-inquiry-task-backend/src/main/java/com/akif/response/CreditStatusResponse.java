package com.akif.response;

import com.akif.enums.CreditStatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreditStatusResponse implements Response {
    private final CreditStatusEnum creditStatusEnum;
    private final double creditLimit;
}
