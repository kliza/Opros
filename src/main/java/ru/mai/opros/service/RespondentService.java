package ru.mai.opros.service;

import ru.mai.opros.dto.RespondentDto;

import java.util.UUID;

public interface RespondentService {
    RespondentDto saveAnswers(UUID id, RespondentDto respondentDto);
}
