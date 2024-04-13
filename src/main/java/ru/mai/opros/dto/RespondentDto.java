package ru.mai.opros.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RespondentDto {
    private UUID id;
    private String fio;
    private String email;
    private UUID pollId;
    private List<RespondentAnswerDto> answers;
}
