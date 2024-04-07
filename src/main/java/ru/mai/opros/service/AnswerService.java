package ru.mai.opros.service;


import ru.mai.opros.entity.AnswerParam;

import java.util.UUID;

public interface AnswerService {
    AnswerParam createParams(UUID questionId, String value);

    AnswerParam updateParams(UUID id, String value);

    void deleteParams(UUID id);

    AnswerParam createParams(UUID id);
}
