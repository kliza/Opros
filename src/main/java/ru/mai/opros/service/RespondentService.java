package ru.mai.opros.service;

import ru.mai.opros.entity.Respondent;
import ru.mai.opros.entity.RespondentAnswer;

import java.util.UUID;

public interface RespondentService {
    RespondentAnswer addAnswer(UUID questionId);

    Respondent createRespondent(UUID pollId);
}
