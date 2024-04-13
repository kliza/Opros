package ru.mai.opros.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class QuestionDto {
    private UUID id;
    private String value;
    private String type;
    private Integer questionNumber;
    private List<AnswerParamDto> answerParams;
}
