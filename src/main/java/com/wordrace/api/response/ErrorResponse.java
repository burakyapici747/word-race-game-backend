package com.wordrace.api.response;

public class ErrorResponse extends BaseResponse {
    public ErrorResponse(String message) {
        super(false, message);
    }
}
