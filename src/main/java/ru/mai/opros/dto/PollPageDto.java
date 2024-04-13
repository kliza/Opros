package ru.mai.opros.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class PollPageDto {
    private UUID id;
    private Integer pageNumber;
    private List<QuestionDto> questions;
}
