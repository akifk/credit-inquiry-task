package com.akif.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse implements Response {
    private final String message;
    private final long errorCode;

}
