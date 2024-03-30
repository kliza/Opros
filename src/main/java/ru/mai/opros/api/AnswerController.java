package ru.mai.opros.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.opros.generated.api.AnswerApi;
import ru.mai.opros.generated.dto.AnswerParamDto;
import ru.mai.opros.service.AnswerService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AnswerController implements AnswerApi {
    private final AnswerService answerService;

    @Override
    public ResponseEntity<AnswerParamDto> createAnswerParam(UUID questionId, AnswerParamDto answerParamDto) {
        return ResponseEntity.status(201).body(answerService.createParams(questionId, answerParamDto));
    }

    @Override
    public ResponseEntity<AnswerParamDto> updateAnswerParam(UUID questionId, UUID id, AnswerParamDto answerParamDto) {
        return ResponseEntity.ok(answerService.updateParams(id, answerParamDto));
    }

    @Override
    public ResponseEntity<Void> deleteAnswerParam(UUID questionId, UUID id) {
        answerService.deleteParams(id);
        return ResponseEntity.ok().build();
    }

}
