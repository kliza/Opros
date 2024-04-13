package ru.mai.opros.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RespondentAnswerDto {
    private UUID id;
    private UUID questionId;
    private String value;
}
