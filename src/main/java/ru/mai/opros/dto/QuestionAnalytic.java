package ru.mai.opros.dto;

import lombok.Data;
import ru.mai.opros.entity.enums.AnswerType;

import java.util.List;

@Data
public class QuestionAnalytic {
    private Integer number;
    private String text;
    private AnswerType type;
    private List<AnswerAnalytic> answers;
}
