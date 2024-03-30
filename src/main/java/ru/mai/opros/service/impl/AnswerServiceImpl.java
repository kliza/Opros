package ru.mai.opros.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.mai.opros.entity.AnswerParam;
import ru.mai.opros.generated.dto.AnswerParamDto;
import ru.mai.opros.repo.AnswerRepo;
import ru.mai.opros.repo.QuestionRepo;
import ru.mai.opros.service.AnswerService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepo answerRepo;
    private final QuestionRepo questionRepo;
    private final ModelMapper mapper;

    @Override
    public AnswerParamDto createParams(UUID questionId, AnswerParamDto answerParamDto) {
        AnswerParam answerParam = answerRepo.save(new AnswerParam()
                .setValue(answerParamDto.getValue())
                .setType(answerParamDto.getType())
                .setQuestion(questionRepo
                        .findById(questionId)
                        .orElseThrow(() -> new EntityNotFoundException("Вопрос %s не найден".formatted(questionId)))));

        return mapper.map(answerParam, AnswerParamDto.class);
    }

    @Override
    public AnswerParamDto updateParams(UUID id, AnswerParamDto answerParamDto) {
        AnswerParam updated = answerRepo.findById(id)
                .map(answerParam -> answerParam
                        .setValue(answerParamDto.getValue())
                        .setType(answerParamDto.getType()))
                .map(answerRepo::save)
                .orElseThrow(() -> new EntityNotFoundException("Параметры %s не найдены".formatted(id)));

        return mapper.map(updated, AnswerParamDto.class);
    }

    @Override
    public void deleteParams(UUID id) {
        answerRepo.deleteById(id);
    }
}
