package ru.mai.opros.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.mai.opros.entity.Question;
import ru.mai.opros.generated.dto.CreateQuestionRequest;
import ru.mai.opros.generated.dto.QuestionDto;
import ru.mai.opros.repo.PollPageRepo;
import ru.mai.opros.repo.QuestionRepo;
import ru.mai.opros.service.QuestionService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepo questionRepo;
    private final PollPageRepo pollPageRepo;
    private final ModelMapper mapper;

    @Override
    public QuestionDto create(CreateQuestionRequest createQuestionRequest) {
        UUID pageId = createQuestionRequest.getPageId();
        Question question = questionRepo.save(new Question()
                .setValue(createQuestionRequest.getValue())
                .setPage(pollPageRepo.findById(pageId)
                        .orElseThrow(() -> new EntityNotFoundException("Страница опроса %s не найдена".formatted(pageId)))));

        return mapper.map(question, QuestionDto.class);
    }

    @Override
    public QuestionDto update(UUID id, CreateQuestionRequest createQuestionRequest) {
        UUID pageId = createQuestionRequest.getPageId();
        Question updated = questionRepo.findById(id)
                .map(question -> question.setValue(createQuestionRequest.getValue())
                        .setPage(pollPageRepo.findById(pageId)
                                .orElseThrow(() -> new EntityNotFoundException("Страница опроса %s не найдена".formatted(pageId)))))
                .orElseThrow(() -> new EntityNotFoundException("Вопрос %s не найден".formatted(id)));

        return mapper.map(updated, QuestionDto.class);
    }

    @Override
    public void delete(UUID id) {
        questionRepo.deleteById(id);
    }
}
