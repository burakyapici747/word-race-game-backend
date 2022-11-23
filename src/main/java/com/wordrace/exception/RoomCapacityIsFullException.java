package com.wordrace.exception;

public class RoomCapacityIsFullException extends RuntimeException{
    public RoomCapacityIsFullException(String message){
        super(message);
    }
}
