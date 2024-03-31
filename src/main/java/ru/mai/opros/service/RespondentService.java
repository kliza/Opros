package ru.mai.opros.service;

import ru.mai.opros.generated.dto.CreateRespondentRequest;
import ru.mai.opros.generated.dto.QuestionAnswer;
import ru.mai.opros.generated.dto.RespondentAnswers;
import ru.mai.opros.generated.dto.RespondentDto;

import java.util.UUID;

public interface RespondentService {
    RespondentAnswers addAnswer(UUID questionId, QuestionAnswer questionAnswer);

    RespondentDto createRespondent(UUID pollId, CreateRespondentRequest createRespondentRequest);
}
