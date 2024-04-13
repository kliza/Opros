package ru.mai.opros.service;

import ru.mai.opros.entity.Question;

import java.util.UUID;

public interface QuestionService {

    void delete(UUID id);

    Question create(UUID pageId);
}
