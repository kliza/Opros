package ru.mai.opros.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class PollDto {
    private UUID id;
    private String name;
    private List<PollPageDto> pages;
}
