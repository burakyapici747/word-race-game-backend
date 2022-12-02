package com.wordrace.util;

import com.wordrace.constant.ResultMessages;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class GlobalHelper {

    public void checkIfNull(Object object){
        if(Optional.ofNullable(object).isPresent()){
            throw new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA);
        }
    }

    public void checkIfAlreadyExist(Object object){
        if(Objects.nonNull(object)){
            throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
        }
    }

    public <S, T> List<T> listDtoConverter(ModelMapper modelMapper, List<S> source, Class<T> target){
        if(source == null)
            return new ArrayList<>();

        return source.stream()
                .map(sourceElement -> modelMapper.map(sourceElement, target))
                .collect(Collectors.toList());
    }

}