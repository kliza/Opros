package ru.mai.opros.service;

import ru.mai.opros.entity.Question;
import ru.mai.opros.entity.enums.AnswerType;

import java.util.UUID;

public interface QuestionService {

    void delete(UUID id);

    Question create(UUID pageId);

    void update(UUID id, String value, AnswerType type);

    UUID getPollIdByQuestionId(UUID id);
}
