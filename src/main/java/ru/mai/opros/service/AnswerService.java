package ru.mai.opros.service;

import ru.mai.opros.generated.dto.AnswerParamDto;

import java.util.UUID;

public interface AnswerService {
    AnswerParamDto createParams(UUID questionId, AnswerParamDto answerParamDto);

    AnswerParamDto updateParams(UUID id, AnswerParamDto answerParamDto);

    void deleteParams(UUID id);
}
