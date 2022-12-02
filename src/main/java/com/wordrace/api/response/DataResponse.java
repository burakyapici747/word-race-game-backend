package com.wordrace.api.response;

public abstract class DataResponse<T> extends BaseResponse {
    public T data;

    public DataResponse(T data, boolean isSuccess, String message){
        super(isSuccess, message);
        this.data = data;
    }

}
