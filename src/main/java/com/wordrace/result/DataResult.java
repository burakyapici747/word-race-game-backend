package com.wordrace.result;

public abstract class DataResult<T> extends Result{
    public T data;

    public DataResult(T data, boolean isSuccess, String message){
        super(isSuccess, message);
        this.data = data;
    }

}
