package com.wordrace.converter;

import com.wordrace.dto.WordDto;
import com.wordrace.model.Word;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class WordConverter implements Converter<Word, WordDto> {

    private final ModelMapper modelMapper;

    public WordConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public WordDto convert(MappingContext<Word, WordDto> mappingContext) {
        final Word word = mappingContext.getSource();
        final WordDto wordDto = new WordDto();

        wordDto.setId(word.getId().toString());
        wordDto.setText(word.getText());
        wordDto.setLanguage(word.getLanguage());

        return wordDto;
    }
}
