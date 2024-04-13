package ru.mai.opros.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageAnalytic {
    private Integer number;
    private List<QuestionAnalytic> questions;
}
