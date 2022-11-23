package com.wordrace.util;

import com.wordrace.constant.ResultMessages;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class GlobalHelper {
    public void checkIfNullable(Object object){
        if(Optional.ofNullable(object).isPresent()){
            throw new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA);
        }
    }

    public void checkIfAlreadyExist(Object object){
        if(Optional.ofNullable(object).isPresent()){
            throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
        }
    }
}
