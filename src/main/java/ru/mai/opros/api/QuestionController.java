package ru.mai.opros.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.opros.generated.api.QuestionApi;
import ru.mai.opros.generated.dto.CreateQuestionRequest;
import ru.mai.opros.generated.dto.QuestionDto;
import ru.mai.opros.service.QuestionService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class QuestionController implements QuestionApi {
    private final QuestionService questionService;

    @Override
    public ResponseEntity<QuestionDto> createQuestion(CreateQuestionRequest createQuestionRequest) {
        return ResponseEntity.status(201).body(questionService.create(createQuestionRequest));
    }

    @Override
    public ResponseEntity<QuestionDto> updateQuestion(UUID id, CreateQuestionRequest createQuestionRequest) {
        return ResponseEntity.ok(questionService.update(id, createQuestionRequest));
    }

    @Override
    public ResponseEntity<Void> deleteQuestion(UUID id) {
        questionService.delete(id);
        return ResponseEntity.ok().build();
    }

}
