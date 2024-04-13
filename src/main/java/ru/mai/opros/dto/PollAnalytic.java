package ru.mai.opros.dto;

import lombok.Data;

import java.util.List;

@Data
public class PollAnalytic {
    private String pollName;
    private int respondentsCount;
    private List<PageAnalytic> pages;

}
