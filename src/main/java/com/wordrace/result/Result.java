package com.wordrace.result;

public abstract class Result {

    protected boolean isSuccess;
    protected String message;

    public Result(boolean isSuccess, String message){
        this.isSuccess = isSuccess;
        this.message = message;
    }


}
