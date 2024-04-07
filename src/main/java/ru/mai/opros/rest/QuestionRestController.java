package ru.mai.opros.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.opros.entity.enums.AnswerType;
import ru.mai.opros.service.AnswerService;
import ru.mai.opros.service.QuestionService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class QuestionRestController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/questions/{id}")
    public void updateQuestion(@PathVariable UUID id,
                               @RequestParam String value,
                               @RequestParam String type) {
        questionService.update(id, value, AnswerType.ofTitle(type));
    }

    @PostMapping("/answer-params/{id}/update")
    public void updateParam(@PathVariable UUID id,
                            @RequestParam String value) {
        answerService.updateParams(id, value);
    }
}
