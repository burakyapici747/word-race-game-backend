package com.wordrace.result;

public abstract class Result {
    public boolean isSuccess;
    public String message;

    public Result(boolean isSuccess, String message){
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
