package ru.mai.opros.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AnswerParamDto {
    private UUID id;
    private String value;
    private Integer number;
}
