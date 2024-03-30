package ru.mai.opros.service;

import ru.mai.opros.generated.dto.CreateQuestionRequest;
import ru.mai.opros.generated.dto.QuestionDto;

import java.util.UUID;

public interface QuestionService {
    QuestionDto create(CreateQuestionRequest createQuestionRequest);

    QuestionDto update(UUID id, CreateQuestionRequest createQuestionRequest);

    void delete(UUID id);
}
