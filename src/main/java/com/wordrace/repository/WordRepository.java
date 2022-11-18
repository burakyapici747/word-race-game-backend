package com.wordrace.repository;

import com.wordrace.model.Language;
import com.wordrace.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {

    Optional<Word> findByText(String text);

    Optional<Word> findByTextAndLanguage(String text, Language language);
}
