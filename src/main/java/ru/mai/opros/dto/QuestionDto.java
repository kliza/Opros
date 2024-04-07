package ru.mai.opros.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class QuestionDto {
    private String value;
    private String type;
    private Integer questionNumber;
    private List<AnswerParamDto> answerParams;
}
