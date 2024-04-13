package ru.mai.opros.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum AnswerType {
    SINGLE_CHOICE("Одиночный выбор"),
    MULTIPLE_CHOICE("Множественный выбор"),
    SCALE("Шкала");

    private final String title;
    private static final Map<String, AnswerType> byTitle = Arrays.stream(AnswerType.values())
            .collect(Collectors.toMap(AnswerType::getTitle, Function.identity()));

    public static AnswerType ofTitle(String title) {
        return byTitle.get(title);
    }
}
