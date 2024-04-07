package ru.mai.opros.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PollPageDto {
    private Integer pageNumber;
    private List<QuestionDto> questions;
}
