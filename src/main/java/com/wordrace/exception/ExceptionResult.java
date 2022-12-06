package com.wordrace.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Data
public class ExceptionResult {
    private HttpStatus statusCode;
    private String message;
    private String path;
    private ZonedDateTime dateTime;
}
