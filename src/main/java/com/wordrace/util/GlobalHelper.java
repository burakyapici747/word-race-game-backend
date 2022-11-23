package com.wordrace.util;

import com.wordrace.constant.ResultMessages;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public <S, T> List<T> listDtoConverter(ModelMapper modelMapper, List<S> source, Class<T> target){
        return source.stream()
                .map(sourceElement -> modelMapper.map(source, target))
                .collect(Collectors.toList());
    }
}