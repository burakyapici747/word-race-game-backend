package com.wordrace.result;

public class ErrorDataResult<T> extends DataResult<T>{
    public ErrorDataResult(T data, String message) {
        super(data, false, message);
    }
}
