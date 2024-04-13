package ru.mai.opros.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mai.opros.entity.Question;
import ru.mai.opros.repo.PollPageRepo;
import ru.mai.opros.repo.QuestionRepo;
import ru.mai.opros.service.QuestionService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepo questionRepo;
    private final PollPageRepo pollPageRepo;

    @Override
    public void delete(UUID id) {
        questionRepo.deleteById(id);
    }

    @Override
    public Question create(UUID pageId) {
        return pollPageRepo.findById(pageId)
                .map(page -> new Question()
                        .setPage(page)
                        .setValue(""))
                .map(questionRepo::save)
                .orElseThrow(() -> new EntityNotFoundException("Страница %s не найдена".formatted(pageId)));
    }
}
