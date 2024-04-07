package ru.mai.opros.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mai.opros.entity.AnswerParam;
import ru.mai.opros.repo.AnswerParamsRepo;
import ru.mai.opros.repo.QuestionRepo;
import ru.mai.opros.service.AnswerService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerParamsRepo answerParamsRepo;
    private final QuestionRepo questionRepo;

    @Override
    public AnswerParam createParams(UUID questionId, String value) {
        return answerParamsRepo.save(new AnswerParam()
                .setValue(value)
                .setQuestion(questionRepo
                        .findById(questionId)
                        .orElseThrow(() -> new EntityNotFoundException("Вопрос %s не найден".formatted(questionId)))));
    }

    @Override
    public AnswerParam updateParams(UUID id, String value) {
        return answerParamsRepo.findById(id)
                .map(answerParam -> answerParam
                        .setValue(value))
                .map(answerParamsRepo::save)
                .orElseThrow(() -> new EntityNotFoundException("Параметры %s не найдены".formatted(id)));
    }

    @Override
    public void deleteParams(UUID id) {
        answerParamsRepo.deleteById(id);
    }

    @Override
    public AnswerParam createParams(UUID id) {
        return questionRepo.findById(id)
                .map(question -> {
                    AnswerParam answerParam = answerParamsRepo.save(new AnswerParam()
                            .setQuestion(question));
                    question.getAnswerParams().add(answerParam);

                    return answerParam;
                })
                .orElseThrow(() -> new EntityNotFoundException("Вопрос %s не найден".formatted(id)));
    }
}
