package ru.mai.opros.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.opros.generated.api.RespondentApi;
import ru.mai.opros.generated.dto.CreateRespondentRequest;
import ru.mai.opros.generated.dto.QuestionAnswer;
import ru.mai.opros.generated.dto.RespondentAnswers;
import ru.mai.opros.generated.dto.RespondentDto;
import ru.mai.opros.service.RespondentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RespondentController implements RespondentApi {
    private final RespondentService respondentService;

    @Override
    public ResponseEntity<RespondentAnswers> addRespondentAnswer(UUID questionId, QuestionAnswer questionAnswer) {
        return ResponseEntity.status(201).body(respondentService.addAnswer(questionId, questionAnswer));
    }

    @Override
    public ResponseEntity<RespondentDto> createRespondent(UUID pollId, CreateRespondentRequest createRespondentRequest) {
        return ResponseEntity.status(201).body(respondentService.createRespondent(pollId, createRespondentRequest));
    }
}
